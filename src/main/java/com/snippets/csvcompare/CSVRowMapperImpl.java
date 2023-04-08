package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CSVRowMapperImpl implements CSVRowMapper {

    public static final String COMMA = ",";

    @Autowired
    ColumnPositionToNameConverter columnPositionToNameConverter;

    @Override
    public RowElement map(String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        HashMap<String, String> recordMap = new HashMap<>();
        ListIterator<String> valueIterator = values.listIterator();
        while (valueIterator.hasNext()) {
            recordMap.put(columnPositionToNameConverter.convert(valueIterator.nextIndex()), valueIterator.next());
        }
        return new RowElement(recordMap);
    }
}
