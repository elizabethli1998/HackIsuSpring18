file#file: InterviewModel.py
/root/anotherproject/utils.py
/root/anotherproject/__init__.py
def input_fn(data_file, num_epochs, shuffle, batch_size):
  """Generate an input function for the Estimator."""
  assert tf.gfile.Exists(data_file), (
      '%s not found. Please make sure you have either run data_download.py or '
      'set both arguments --train_data and --test_data.' % data_file)

  def parse_csv(value):
    print('Parsing', data_file)
    columns = tf.decode_csv(value, record_defaults=_CSV_COLUMN_DEFAULTS)
    features = dict(zip(_CSV_COLUMNS, columns))
    labels = features.pop('Observed Attendance')
    return features, tf.equal(labels, 'Yes')

  # Extract lines from input files using the Dataset API.
  dataset = tf.data.TextLineDataset(data_file)

  if shuffle:
    dataset = dataset.shuffle(buffer_size=_SHUFFLE_BUFFER)

  dataset = dataset.map(parse_csv, num_parallel_calls=6)

  # We call repeat after shuffling, rather than before, to prevent separate
  # epochs from blending together.
  dataset = dataset.repeat(num_epochs)
  dataset = dataset.batch(batch_size)

  iterator = dataset.make_one_shot_iterator()
  features, labels = iterator.get_next()
  return features, labels
  
	industry = tf.feature_column.categorical_column_with_vocabulary_list(
    'Industry', [
        'Pharmaceuticals', 'IT Services', 'BFSI', 'IT Products and Services', 'Electronics',
        'Telecom', 'IT'])
		
	position = tf.feature_column.categorical_column_with_vocabulary_list(
    'Position to be closed', [
        'Production- Sterile', 'Selenium testing', 'Dot Net', 'AML', 'Trade Finance',
        'Routine', 'Niche'])
	 
	type = tf.feature_column.categorical_column_with_vocabulary_list(
    'Interview Type', [
        'Scheduled Walkin', 'Scheduled', 'Walkin', 'Scheduled Walk In'])	
	
	gender = tf.feature_column.categorical_column_with_vocabulary_list(
    'Gender', [
        'Male', 'Female'])	
	
	status = tf.feature_column.categorical_column_with_vocabulary_list(
    'Marital Status', [
        'Single', 'Married'])	
		
	skillset = tf.feature_column.categorical_column_with_hash_bucket(
    'Skillset', hash_b
	ucket_size=1000)	
		
	skillset_x_position = tf.feature_column.crossed_column(
    ['Skillset', 'Position'], hash_bucket_size=1000)	
	
	skillset_x_industry = tf.feature_column.crossed_column(
    ['Skillset', 'Industry'], hash_bucket_size=1000)	
	
	base_columns = [
    industry, position, interview type, gender, marital status,
    skillset,
	]
	crossed_columns = [
    tf.feature_column.crossed_column(
        ['Skillset', 'Industry'], hash_bucket_size=1000),
    tf.feature_column.crossed_column(
        ['Skillset', 'Position'], hash_bucket_size=1000),
	]

	model_dir = tempfile.mkdtemp()
	model = tf.estimator.LinearClassifier(
		model_dir=model_dir, feature_columns=base_columns + crossed_columns)
