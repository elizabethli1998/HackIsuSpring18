package hackisu.hackisuspring18;

/**
 * Created by elizabethli on 3/23/18.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ArrayList<String> read(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine = reader.readLine();
            Scanner scan = new Scanner(csvLine);
            scan.useDelimiter(",");
            ArrayList<String> ALS = new ArrayList<String>();


           while(scan.hasNext())
           {
                ALS.add(scan.next());

            }

            return ALS;

        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
//        finally {
//            try {
//                inputStream.close();
//            }
//            catch (IOException e) {
//                throw new RuntimeException("Error while closing input stream: "+e);
//            }
//        }
//        return resultList;

    }
}

