package com.snippets.csvcompare;

import java.util.Objects;

public class ComparisonResult {

    private final String oldValue;
    private final String newValue;

    public ComparisonResult(String oldValue, String newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public boolean isDifferent() {
        return Objects.equals(oldValue, newValue);
    }

}
