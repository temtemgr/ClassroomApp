package io;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class FileChooser {
    public static Map<String,String> selectFileWithExplorerDialog() {
        // Opens file dialog where the file will be chosen from
        FileDialog dialog = new FileDialog((Frame) null, "Open File");
        dialog.setVisible(true);

        Map<String,String> path = new HashMap<>();
        path.put("fileName",dialog.getFile());
        path.put("directory", dialog.getDirectory());

        return path;
    }
}
