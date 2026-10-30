package io;

import logic.Course;

import java.nio.file.Path;

public final class CourseDataReaderFactory {

    // factory definition: A factory's job is to create objects for other code.
    // A factory is a static helper method
    // The caller says what it needs, and the factory decides which class to instantiate and how.
    // Class could also be named: CourseDataReaders -> with method: .forFile()
    // Static create method could also be part of a CourseDataReader interface.

    public static DataReader<Course> create(Path file) throws DataReadException {
        if (file.getFileName().toString().endsWith(".csv")) {
            return new CsvCourseDataReader();
        }
        if (file.getFileName().toString().endsWith(".txt")) {
            return new TxtCourseDataReader();
        }
        throw new DataReadException("The selected file format is not supported");
    }
}
