package com.snippets.csvcompare;

public class ColumnPositionToNameConverterImpl implements ColumnPositionToNameConverter {
    @Override
    public String convert(int position) {
        return switch (position) {
            case 1 -> "Column1";
            case 2 -> "Column2";
            case 3 -> "Column3";
            default -> throw new IllegalStateException("Unknown Column position [" + position + "]");
        };
    }
}
