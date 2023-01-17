package models.FileOperations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.Objects.Stopky;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONFileOperation implements FileOperation{

    JSONObject json;
    JSONArray data = new JSONArray();
    ArrayList<String> s = new ArrayList<>();

    public JSONFileOperation() {
        json = new JSONObject();
    }

    @Override
    public void Save(ArrayList<Stopky> values) throws IOException {
        FileWriter file = new FileWriter("E:output.json");
        for(int i = 0; i< values.size();i++)
        {
            json = new JSONObject();
            json.put("TIME",values.get(i).toString());

            //Zabalit do JSON OBJEKTU je treba
            JSONObject timeObject = new JSONObject();
            timeObject.put("cas",json);

            data.add(timeObject);
        }
        file.write(data.toJSONString());
        file.flush();
        file.close();
    }

    @Override
    public ArrayList<String> Load() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        s = new ArrayList<>();

        try (FileReader reader = new FileReader("E:output.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
            return s;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseEmployeeObject(JSONObject employee)
    {
        //Get JSON object within list
        JSONObject employeeObject = (JSONObject) employee.get("cas");

        //Get Value
        String firstName = (String) employeeObject.get("TIME");
        s.add(firstName);
        System.out.println(firstName);
    }
}
