package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CSVRowMapperImpl implements CSVRowMapper {

    public static final String COMMA = ",";

    private final ColumnPositionToNameConverter columnPositionToNameConverter;

    @Autowired
    public CSVRowMapperImpl(ColumnPositionToNameConverter columnPositionToNameConverter) {
        this.columnPositionToNameConverter = columnPositionToNameConverter;
    }

    @Override
    public RowElement map(String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        return new RowElement(IntStream.range(0, values.size())
                .boxed()
                .collect(Collectors.toMap(
                        i -> columnPositionToNameConverter.convert(i),
                        i -> values.get(i).strip())));
    }
}
