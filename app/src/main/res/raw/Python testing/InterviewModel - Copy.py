# Copyright 2017 The TensorFlow Authors. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================
"""Example code for TensorFlow Wide & Deep Tutorial using tf.estimator API."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import os
import shutil
import sys

import tensorflow as tf  # pylint: disable=g-bad-import-order

from official.utils.arg_parsers import parsers
from official.utils.logging import hooks_helper

_CSV_COLUMNS = [
    'Industry', 'Position to be closed', 'Interview Type', 'Gender', 'Marital Status',
    'Skillset'
]

_CSV_COLUMN_DEFAULTS = [[''], [''], [''], [''], [''], ['']]
                        

_NUM_EXAMPLES = {
    'train': 32561,
    'validation': 16281,
}


def build_model_columns():
  """Builds a set of wide and deep feature columns."""
  
  industry = tf.feature_column.categorical_column_with_vocabulary_list(    'Industry', [
          'Pharmaceuticals', 'IT Services', 'BFSI', 'IT Products and Services', 'Electronics',
          'Telecom', 'IT'])

  position = tf.feature_column.categorical_column_with_vocabulary_list(
      'Position to be closed', [
          'Production- Sterile', 'Selenium testing', 'Dot Net', 'AML', 'Trade Finance',
          'Routine', 'Niche'])
           
  tipe = tf.feature_column.categorical_column_with_vocabulary_list(
      'Interview Type', [
          'Scheduled Walkin', 'Scheduled', 'Walkin', 'Scheduled Walk In'])	
          
  gender = tf.feature_column.categorical_column_with_vocabulary_list(
      'Gender', [
          'Male', 'Female'])	
          
  status = tf.feature_column.categorical_column_with_vocabulary_list(
      'Marital Status', [
          'Single', 'Married'])	
                  
  skillset = tf.feature_column.categorical_column_with_hash_bucket(
      'Skillset', hash_bucket_size=1000)	
                  
  skillset_x_position = tf.feature_column.crossed_column(
      ['Skillset', 'Position'], hash_bucket_size=1000)	
          
  skillset_x_industry = tf.feature_column.crossed_column(
      ['Skillset', 'Industry'], hash_bucket_size=1000)	
          
  base_columns = [
      industry, position, tipe, gender, status,
      skillset,
          ]
  crossed_columns = [
      tf.feature_column.crossed_column(
          ['Skillset', 'Industry'], hash_bucket_size=1000),
      tf.feature_column.crossed_column(
          ['Skillset', 'Position'], hash_bucket_size=1000),
          ]


  wide_columns = base_columns + crossed_columns

  deep_columns = [
   ]

  return wide_columns, deep_columns


def build_estimator(model_dir, model_type):
  """Build an estimator appropriate for the given model type."""
  wide_columns, deep_columns = build_model_columns()
  hidden_units = [100, 75, 50, 25]

  # Create a tf.estimator.RunConfig to ensure the model is run on CPU, which
  # trains faster than GPU for this model.
  run_config = tf.estimator.RunConfig().replace(
      session_config=tf.ConfigProto(device_count={'GPU': 0}))

  if model_type == 'wide':
    return tf.estimator.LinearClassifier(
        model_dir=model_dir,
        feature_columns=wide_columns,
        config=run_config)
  elif model_type == 'deep':
    return tf.estimator.DNNClassifier(
        model_dir=model_dir,
        feature_columns=deep_columns,
        hidden_units=hidden_units,
        config=run_config)
  else:
    return tf.estimator.DNNLinearCombinedClassifier(
        model_dir=model_dir,
        linear_feature_columns=wide_columns,
        dnn_feature_columns=deep_columns,
        dnn_hidden_units=hidden_units,
        config=run_config)


def input_fn(data_file, num_epochs, shuffle, batch_size):
  """Generate an input function for the Estimator."""
  assert tf.gfile.Exists(data_file), (
      '%s not found. Please make sure you have run data_download.py and '
      'set the --data_dir argument to the correct path.' % data_file)

  def parse_csv(value):
    print('Parsing', data_file)
    columns = tf.decode_csv(value, record_defaults=_CSV_COLUMN_DEFAULTS)
    features = dict(zip(_CSV_COLUMNS, columns))
    labels = features.pop('Observed Attendance')
    return features, tf.equal(labels, 'Yes')

  # Extract lines from input files using the Dataset API.
  dataset = tf.data.TextLineDataset(data_file)

  if shuffle:
    dataset = dataset.shuffle(buffer_size=_NUM_EXAMPLES['train'])

  dataset = dataset.map(parse_csv, num_parallel_calls=5)

  # We call repeat after shuffling, rather than before, to prevent separate
  # epochs from blending together.
  dataset = dataset.repeat(num_epochs)
  dataset = dataset.batch(batch_size)
  return dataset


def main(argv):
  parser = WideDeepArgParser()
  flags = parser.parse_args(args=argv[1:])

  # Clean up the model directory if present
  shutil.rmtree(flags.model_dir, ignore_errors=True)
  model = build_estimator(flags.model_dir, flags.model_type)

  train_file = os.path.join(flags.data_dir, 'adult.data')
  test_file = os.path.join(flags.data_dir, 'adult.test')

  # Train and evaluate the model every `FLAGS.epochs_per_eval` epochs.
  def train_input_fn():
    return input_fn(train_file, flags.epochs_per_eval, True, flags.batch_size)

  def eval_input_fn():
    return input_fn(test_file, 1, False, flags.batch_size)

  train_hooks = hooks_helper.get_train_hooks(
      flags.hooks, batch_size=flags.batch_size,
      tensors_to_log={'average_loss': 'head/truediv',
                      'loss': 'head/weighted_loss/Sum'})

  # Train and evaluate the model every `FLAGS.epochs_between_evals` epochs.
  for n in range(flags.train_epochs // flags.epochs_between_evals):
    model.train(input_fn=train_input_fn, hooks=train_hooks)
    results = model.evaluate(input_fn=eval_input_fn)

    # Display evaluation metrics
    print('Results at epoch', (n + 1) * flags.epochs_between_evals)
    print('-' * 60)

    for key in sorted(results):
      print('%s: %s' % (key, results[key]))


class WideDeepArgParser(argparse.ArgumentParser):
  """Argument parser for running the wide deep model."""

  def __init__(self):
    super(WideDeepArgParser, self).__init__(parents=[parsers.BaseParser()])
    self.add_argument(
        '--model_type', '-mt', type=str, default='wide_deep',
        choices=['wide', 'deep', 'wide_deep'],
        help='[default %(default)s] Valid model types: wide, deep, wide_deep.',
        metavar='<MT>')
    self.set_defaults(
        data_dir='/tmp/census_data',
        model_dir='/tmp/census_model',
        train_epochs=40,
        epochs_between_evals=2,
        batch_size=40)


if __name__ == '__main__':
  tf.logging.set_verbosity(tf.logging.INFO)
  main(argv=sys.argv)