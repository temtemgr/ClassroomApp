package io;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorMapReader extends DataReader{

    public MajorMapReader() {
        super();
    }

    public Map<String,String> createFieldsOfStudy() {
        Map<String,String> fieldsOfStudy = new HashMap<>();

        for (String fieldOfStudy: dataText) {
            String[] parts = fieldOfStudy.split("\t", 2);

            if (parts.length != 2) {
                System.out.println("The text in the file has a wrong format. Take a look at the template.");
                System.exit(1);
            }

            fieldsOfStudy.put(parts[0], parts[1]);
        }
        return fieldsOfStudy;
    }
}
