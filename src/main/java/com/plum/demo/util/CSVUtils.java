package com.plum.demo.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CSVUtils {
    private static final CsvMapper mapper = new CsvMapper();

    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        mapper.enable(CsvParser.Feature.EMPTY_STRING_AS_NULL);
        CsvSchema schema = mapper.typedSchemaFor(clazz)
                .withHeader()
                .withColumnReordering(true)
                .withNullValue(null);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
}
