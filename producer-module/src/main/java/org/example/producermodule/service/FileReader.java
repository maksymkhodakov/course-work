package org.example.producermodule.service;

import java.util.List;

public interface FileReader {
    <T> List<T> parseData(byte[] bytes, String filename, Class<T> clazz);
}
