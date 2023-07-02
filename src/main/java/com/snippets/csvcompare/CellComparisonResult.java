package com.snippets.csvcompare;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class CellComparisonResult {

    private final String key;
    private final String oldValue;
    private final String newValue;

    public boolean isNotDifferent() {
        return !Objects.equals(oldValue, newValue);
    }

}
