package hackisu.hackisuspring18;


import org.tensorflow.Graph;
import org.tensorflow.Tensor;
import org.tensorflow.Session;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Smith on 24-Mar-18.
 */

public class App {

    public static void main(String args[]) {
        System.out.println(" Hello World!");
        String inputname = "text.txt";
        try {
            FileReader input = new FileReader(inputname);
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Unable to open file '" +
                            inputname + "'");
        }
        try (Graph g = new Graph()) {


            try (Tensor t = Tensor.create(args[0].getBytes("UTF-8"))) {
                //g.opBuilder("Const", "MyConst").set
                g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build();

                try (Session s = new Session(g);
                     Tensor output = s.runner().fetch("MyConst").run().get(0)) {
                    System.out.println(new String(output.bytesValue(), "UTF-8"));
                }
            }
            catch(UnsupportedEncodingException e)
            {

            }
        }
    }
}

