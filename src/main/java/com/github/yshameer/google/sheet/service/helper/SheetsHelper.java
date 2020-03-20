package com.github.yshameer.google.sheet.service.helper;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class SheetsHelper {

    private final Sheets sheets;


    public static final int INDEX_ROW_WITH_COLUMN_NAMES = 0;
    public static final int INDEX_ROW_WITH_DATA_START = 1;
    public static final int DEFAULT_INDEX_SHIFT = 1;
    private static final int ALPHABET_LETTERS_NUMBER = 26;

    public Spreadsheet getSpreadsheet(String spreadsheetId) {
        try {
            return sheets.spreadsheets().get(spreadsheetId).execute();
        } catch (GoogleJsonResponseException googleException) {
            throw new RuntimeException(googleException);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public List<List<String>> getData(String spreadsheetId, String worksheetName) {
        return getDataObjects(spreadsheetId, worksheetName).stream()
                .map(list -> list.stream().map(Object::toString).collect(toList()))
                .collect(toList());
    }

    private List<List<Object>> getDataObjects(String spreadsheetId, String worksheetName) {
        try {
            return sheets.spreadsheets()
                    .values()
                    .get(spreadsheetId, worksheetName)
                    .execute()
                    .getValues();
        } catch (GoogleJsonResponseException googleException) {
            throw new RuntimeException(googleException);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static String columnIndexToLetterId(int columnIndex) {
        if (columnIndex < 0) throw new IllegalArgumentException("Column index must be >= 0");
        StringBuilder columnId = new StringBuilder();

        while (columnIndex >= 0) {
            int temp = columnIndex % ALPHABET_LETTERS_NUMBER;
            columnId.insert(0, (char) ('A' + temp));
            columnIndex = (columnIndex - temp) / ALPHABET_LETTERS_NUMBER - DEFAULT_INDEX_SHIFT;
        }
        return columnId.toString();
    }
}

