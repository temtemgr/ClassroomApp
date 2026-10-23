package io;

import logic.Course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MajorMapReader implements DataReader<Map<String,String>> {

    @Override
    public Map<String,String> readData(String path) {
        List<String> text = new ArrayList<>();
        File dataFile = new File(path);

        // Scans file
        try (Scanner scanner = new Scanner(dataFile)) {
            while (scanner.hasNextLine()) {
                text.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return this.parseFieldsOfStudy(text);

    }

    public Map<String,String> parseFieldsOfStudy(List<String> text) {
        Map<String,String> fieldsOfStudy = new HashMap<>();

        for (String fieldOfStudy: text) {
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
