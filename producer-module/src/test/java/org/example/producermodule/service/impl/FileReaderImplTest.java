package org.example.producermodule.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.zoo.exceptions.OperationException;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FileReaderImpl.class})
@ExtendWith(SpringExtension.class)
class FileReaderImplTest {
    @Autowired
    private FileReaderImpl fileReaderImpl;

    /**
     * Method under test: {@link FileReaderImpl#parseData(byte[], String, Class)}
     */
    @Test
    void testParseData1() throws UnsupportedEncodingException {
        // Arrange
        byte[] bytes = "AXAXAXAX".getBytes("UTF-8");
        Class<Object> clazz = Object.class;

        // Act and Assert
        assertThrows(OperationException.class, () -> fileReaderImpl.parseData(bytes, "foo.txt", clazz));
    }

    /**
     * Method under test: {@link FileReaderImpl#parseData(byte[], String, Class)}
     */
    @Test
    void testParseData2() throws UnsupportedEncodingException {
        // Arrange
        byte[] bytes = "XXAXAXAX".getBytes("UTF-8");
        Class<Object> clazz = Object.class;

        // Act and Assert
        assertThrows(OperationException.class, () -> fileReaderImpl.parseData(bytes, "foo.txt", clazz));
    }

    /**
     * Method under test: {@link FileReaderImpl#parseData(byte[], String, Class)}
     */
    @Test
    void testParseData() throws UnsupportedEncodingException {
        // Arrange
        byte[] bytes = "AXAXAXAX".getBytes("UTF-8");
        Class<Object> clazz = Object.class;

        // Act and Assert
        assertTrue(fileReaderImpl.parseData(bytes, ".csv", clazz).isEmpty());
    }
}
