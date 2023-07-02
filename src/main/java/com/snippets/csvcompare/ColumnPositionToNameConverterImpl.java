package com.snippets.csvcompare;

import org.springframework.stereotype.Component;

@Component
public class ColumnPositionToNameConverterImpl implements ColumnPositionToNameConverter {
    @Override
    public String convert(int position) {
        switch (position) {
            case 1:  return "Column1";
            case 2:  return "Column2";
            case 3:  return "Column3";
            default: throw new IllegalStateException("Unknown Column position [" + position + "]");
        }
    }
}
