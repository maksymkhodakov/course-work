package org.example.producermodule.service.impl;

import com.example.zoo.exceptions.ApiErrors;
import com.example.zoo.exceptions.OperationException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.poiji.annotation.ExcelRow;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.example.producermodule.service.FileReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class FileReaderImpl implements FileReader {
    public static final List<String> ALLOWED_EXTENSIONS = List.of("xls", "xlsx", "csv");

    @Override
    @SneakyThrows
    public <T> List<T> parseData(byte[] bytes, String filename, Class<T> clazz) {
        validateFileExtensions(filename);
        final List<T> data;
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            if (filename.contains(".csv")) {
                data = parseFromCSV(inputStream, clazz);
            } else {
                data = parseFromExcel(inputStream, getExcelType(filename), clazz);
            }
        }
        return data;
    }

    private <T> List<T> parseFromCSV(InputStream inputStream, Class<T> clazz) throws IOException, IllegalAccessException {
        try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            final CsvToBean<T> csvMapper = new CsvToBeanBuilder<T>(inputStreamReader)
                    .withType(clazz)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withSkipLines(1)
                    .build();

            final List<T> data = csvMapper.parse();
            final List<Field> fields = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelRow.class);

            if (!fields.isEmpty()) {
                int size = data.size();
                final Field field = fields.get(0);
                for (int i = 0; i < size; i++) {
                    final T dataToProcess = data.get(i);
                    field.setAccessible(true);
                    field.set(dataToProcess, i + 1);
                }
            }
            return data;
        }
    }

    private <T> List<T> parseFromExcel(InputStream inputStream, PoijiExcelType excelType, Class<T> clazz) {
        final PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        return Poiji.fromExcel(inputStream, excelType, clazz, options);
    }

    private PoijiExcelType getExcelType(String filename) {
        return filename.endsWith(".xls") ? PoijiExcelType.XLS : PoijiExcelType.XLSX;
    }

    private void validateFileExtensions(String filename) {
        if (!FilenameUtils.isExtension(filename, ALLOWED_EXTENSIONS)) {
            throw new OperationException(ApiErrors.WRONG_FILE_EXTENSION);
        }
    }
}
