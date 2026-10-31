package ui;

import io.DataReadException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;

public final class FileChooserFx {
    public static Path selectFileWithExplorerDialog(String title, Window owner) throws DataReadException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("CSV Files", "*.csv"),
                new ExtensionFilter("All Files", "*.*")
            );

        File selectedFile = fileChooser.showOpenDialog(owner);
        if (selectedFile == null) {
            throw new DataReadException("No file selected");
        }
        return selectedFile.toPath();
    }
}