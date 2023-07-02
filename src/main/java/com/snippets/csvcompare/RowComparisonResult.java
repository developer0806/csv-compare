package com.snippets.csvcompare;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class RowComparisonResult {
    private String key;
    private List<CellComparisonResult> cellComparisonResults;

    public boolean hasNoDifference() {
        return cellComparisonResults.parallelStream().noneMatch(CellComparisonResult::isDifferent);
    }
}
