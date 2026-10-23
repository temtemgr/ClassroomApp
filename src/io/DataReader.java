package io;

public interface DataReader<ObjectType> {
    ObjectType readData(String path);
}
