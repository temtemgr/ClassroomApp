package io;

import java.nio.file.Path;

public interface DataReader<T> {
    T readData(Path file) throws DataReadException;
}
