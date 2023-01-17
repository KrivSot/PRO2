package models.FileOperations;

import models.Objects.Stopky;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CVSFileOperation {

    ArrayList<String> files = new ArrayList<>();
    final String FILEPATH = "E:output.csv";

    public void save(ArrayList<Stopky> values) throws IOException {
        FileWriter file = new FileWriter(FILEPATH);
        for(int i = 0; i< values.size();i++)
        {
            file.write(values.get(i)+";"+i+"\n");
        }
        file.flush();
        file.close();
    }

    public ArrayList<String> load()
    {
        String line = "";
        String splitBy = ";";
        try
        {
//parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] employee = line.split(splitBy);    // use comma as separator
                System.out.println("Employee [First Name=" + employee[0] + ", Last Name=" + employee[1]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
