package models.FileOperations;

import models.Objects.Stopky;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface FileOperation {
    void Save(ArrayList<String> Collumname, ArrayList<Stopky> values) throws IOException;
    ArrayList<String> Load() throws IOException, ParseException;
}
