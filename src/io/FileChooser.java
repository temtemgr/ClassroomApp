package io;

import java.awt.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class FileChooser {
    public static Path selectFileWithExplorerDialog() throws DataReadException {
        // Opens file dialog where the file will be chosen from
        FileDialog dialog = new FileDialog((Frame) null, "Open File");
        dialog.setVisible(true);

        if (dialog.getFile().isEmpty()) {
            throw new DataReadException("No file selected");
        }

        return Path.of(dialog.getDirectory() + dialog.getFile());
    }
}
