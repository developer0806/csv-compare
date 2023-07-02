package com.snippets.csvcompare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CSVRowMapperImplTest {

    public static final String VALUE_1 = "value1";
    public static final String VALUE_2 = "value2";
    public static final String VALUE_3 = "value3";
    public static final String COLUMN_1 = "Column1";
    public static final String COLUMN_2 = "Column2";
    public static final String COLUMN_3 = "Column3";
    @InjectMocks
    CSVRowMapperImpl mapper;
    @Mock
    private ColumnPositionToNameConverter columnPositionToNameConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void map_whenThreeColumns_ThenMapSizeIsThree() {

        when(columnPositionToNameConverter.convert(0)).thenReturn(COLUMN_1);
        when(columnPositionToNameConverter.convert(1)).thenReturn(COLUMN_2);
        when(columnPositionToNameConverter.convert(2)).thenReturn(COLUMN_3);
        RowElement rowElement = mapper.map(VALUE_1 + ", " + VALUE_2 + ", " + VALUE_3);
        assertEquals(3, rowElement.getMap().size());
        assertEquals(VALUE_1,rowElement.getMap().get(COLUMN_1));
        assertEquals(VALUE_2,rowElement.getMap().get(COLUMN_2));
        assertEquals(VALUE_3,rowElement.getMap().get(COLUMN_3));

    }
}