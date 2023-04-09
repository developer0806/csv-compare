package com.snippets.csvcompare;

import java.util.Objects;

public class ComparisonResult {

    String oldValue;
    String newValue;

    public void ComparisonResult(String oldValue, String newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public boolean isDifferent() {
        return Objects.equals(oldValue, newValue);
    }

}
