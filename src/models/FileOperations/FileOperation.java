package models.FileOperations;

import models.Objects.Stopky;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public interface FileOperation {
    void Save(ArrayList<Stopky> values) throws IOException;
    ArrayList<String> Load() throws IOException, ParseException;
}
