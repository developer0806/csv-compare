package com.snippets.csvcompare;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultFileComparator implements FileComparator {

    @Override
    public Stream<RowComparisonResult> compare(ConcurrentMap<String, RowElement> oldData,
                                               ConcurrentMap<String, RowElement> newData) {
        return getComparisonRequestMap(oldData, newData).map(this::compareRow);
    }

    private RowComparisonResult compareRow(RowComparisonRequest rowComparisonRequest) {
        return RowComparisonResult.builder().key(rowComparisonRequest.getKey())
                .cellComparisonResults(
                        rowComparisonRequest.getNewData().getMap().keySet().parallelStream()
                                .map(key -> getCellComparisonResult(rowComparisonRequest, key))
                                .collect(Collectors.toList()))
                .build();
    }

    private static CellComparisonResult getCellComparisonResult(RowComparisonRequest rowComparisonRequest, String key) {
        return new CellComparisonResult(key,
                rowComparisonRequest.getNewData().getMap().get(key),
                rowComparisonRequest.getOldData().getMap().get(key));
    }


    private Stream<RowComparisonRequest> getComparisonRequestMap(final ConcurrentMap<String, RowElement> dataFromOldFile,
                                                                 final ConcurrentMap<String, RowElement> dataFromNewFile) {
        return dataFromOldFile.keySet().stream().parallel()
                .map(key -> RowComparisonRequest.builder()
                        .key(key)
                        .oldData(dataFromOldFile.get(key))
                        .newData(dataFromNewFile.get(key))
                        .build());
    }
}
