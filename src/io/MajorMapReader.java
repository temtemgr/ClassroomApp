package io;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MajorMapReader implements DataReader<Map<String,String>> {

    @Override
    public Map<String,String> readData(Path file) throws DataReadException {
        List<String> text;
        try {
            text = Files.readAllLines(file);
        } catch (IOException e) {
            throw new DataReadException(e.getMessage());
        }

        return this.parseFieldsOfStudy(text);
    }

    public Map<String,String> parseFieldsOfStudy(List<String> text) throws DataReadException {
        Map<String,String> fieldsOfStudy = new HashMap<>();

        for (String fieldOfStudy: text) {
            String[] parts = fieldOfStudy.split("\t", 2);

            if (parts.length != 2) {
                throw new DataReadException("The text in the file has a wrong format. Take a look at the template.");
            }

            fieldsOfStudy.put(parts[0], parts[1]);
        }
        return fieldsOfStudy;
    }
}
