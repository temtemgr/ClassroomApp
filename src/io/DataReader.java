package io;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    public String dirPath;
    private File dataFile;
    private String fileName;
    protected List<String> dataText;

    public String getDirPath() {
        return dirPath;
    }

    protected DataReader() {
        this.setDataFromFile();
    }

    protected void setDataFromFile() {
        this.selectFileWithExplorerDialog();

        // User clicked cancel
        if (fileName == null) {
            System.exit(0);
        }
        // User selected .txt file
        if (fileName.endsWith(".txt")) {
            this.setDataFromTxtFile();
        }
        else {
            System.out.println("The selected file format is not supported");
            System.exit(1);
        }
    }

    private void setDataFromTxtFile() {
        ArrayList<String> text = new ArrayList<>();

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
        this.dataText = text;
    }

    private void selectFileWithExplorerDialog() {
        // Opens file dialog where the file will be chosen from
        FileDialog dialog = new FileDialog((Frame) null, "Open File");
        dialog.setVisible(true);

        this.fileName = dialog.getFile();
        this.dirPath = dialog.getDirectory();
        this.dataFile =  new File(dirPath + fileName);
    }
}
