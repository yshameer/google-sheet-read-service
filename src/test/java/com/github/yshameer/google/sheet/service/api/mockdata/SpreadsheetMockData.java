package com.github.yshameer.google.sheet.service.api.mockdata;


import com.github.yshameer.google.sheet.service.model.Column;
import com.github.yshameer.google.sheet.service.model.Row;
import com.github.yshameer.google.sheet.service.model.SheetData;
import com.github.yshameer.google.sheet.service.model.SheetRequest;
import com.github.yshameer.google.sheet.service.model.Worksheet;
import com.github.yshameer.google.sheet.service.model.WorksheetRequest;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class SpreadsheetMockData {


    public static final String SHEET_NAME = "EXPENSES";
    public static final String SHEET_ID = "sheet_id_1";

    public static final String SUBJECT_WORKSHEET_NAME = "subject";
    public static final String SUBJECT_TO_UNIT_WORKSHEET_NAME = "subject_to_unit";
    public static final int SHEET_DATA_ROW_2 = 2;
    public static final int SHEET_DATA_ROW_3 = 3;
    public static final String SUBJECT_ID_COLUMN_NAME = "subject_id";
    public static final String UNIT_ID_COLUMN_NAME = "unit_id";


    private static List<String> getSubjectColumnNames() {
        return asList(SUBJECT_ID_COLUMN_NAME, "subject_id_type", "subject_type", "subject_title", "question_type", "SPACE ACCELERATION");
    }

    private static List<String> getSubjectToUnitColumnNames() {
        return asList(UNIT_ID_COLUMN_NAME, "unit_id_type", "unit_type", "unit", "unit_relation",
                "unit_relation_type", SUBJECT_ID_COLUMN_NAME, "subject_id_type", "subject_title", "subject_type", "subject_usage");
    }

    public static Spreadsheet getGoogleSpreadsheetExample() {
        SheetProperties subjectSheetProperties = new SheetProperties();
        subjectSheetProperties.setTitle(SUBJECT_WORKSHEET_NAME);
        Sheet subjectSheet = new Sheet();
        subjectSheet.setProperties(subjectSheetProperties);

        SheetProperties subjectToUnitSheetProperties = new SheetProperties();
        subjectToUnitSheetProperties.setTitle(SUBJECT_TO_UNIT_WORKSHEET_NAME);
        Sheet subjectToUnitSheet = new Sheet();
        subjectToUnitSheet.setProperties(subjectToUnitSheetProperties);

        SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
        spreadsheetProperties.setTitle(SHEET_NAME);
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setProperties(spreadsheetProperties);
        spreadsheet.setSheets(asList(subjectSheet, subjectToUnitSheet));
        spreadsheet.setSpreadsheetId(SHEET_ID);

        return spreadsheet;
    }

    public static List<List<String>> getSheetsHelperResponseWithEmptyRows() {
        return asList(
                getSubjectColumnNames(),
                asList("", "", "", ""));
    }

    public static List<List<String>> getSheetsHelperResponseWithEmptyColumns() {
        return asList(
                asList("", "", "", ""),
                asList("", "", "", ""));
    }

    public static List<List<String>> getSheetsHelperResponseWithSubjectData() {
        return asList(
                getSubjectColumnNames(),
                asList("AAAAA", "SCIENCE", "PHYSICS", "1"),
                asList("BBBBB", "SCIENCE", "PHYSICS", "2")
        );
    }

    public static List<List<String>> getSheetsHelperResponseWithSubjectToUnitData() {
        return asList(
                getSubjectToUnitColumnNames(),
                asList("1111", "Space", "Rocket Science", "1.1.1", "Associated to", "Correlates to", "AAAAA", "SCIENCE", "1", "PHYSICS"),
                asList("1111", "Space", "Rocket Science", "1.1.1", "Associated to", "Correlates to", "BBBBB", "SCIENCE", "2", "PHYSICS")
        );
    }


    private static WorksheetRequest.WorksheetRequestBuilder getSubjectToUnitWorksheetRequestBuilder() {
        return WorksheetRequest.builder().sheetName(SUBJECT_TO_UNIT_WORKSHEET_NAME);
    }

    public static WorksheetRequest getSubjectToUnitWorksheetRequestWithNameOnly() {
        return getSubjectToUnitWorksheetRequestBuilder().build();
    }

    public static WorksheetRequest getSubjectToUnitWorksheetRequestWithUnitColumns() {
        return getSubjectToUnitWorksheetRequestBuilder().columns(asList(UNIT_ID_COLUMN_NAME, "unit_id_type", "unit_type", "unit")).build();
    }

    private static SheetRequest.SheetRequestBuilder getSheetRequestBuilder() {
        return SheetRequest.builder().sheetId(SHEET_ID);
    }

    public static SheetRequest getSheetRequestWithIdOnly() {
        return getSheetRequestBuilder().build();
    }


    public static SheetRequest getSheetRequestForSubjectToUnitWorksheet() {
        return getSheetRequestBuilder()
                .sheets(singletonList(getSubjectToUnitWorksheetRequestWithNameOnly()))
                .build();
    }

    public static SheetRequest getSheetRequestForSubjectToUnitWorksheetAndUnitColumns() {
        return getSheetRequestBuilder()
                .sheets(singletonList(getSubjectToUnitWorksheetRequestWithUnitColumns()))
                .build();
    }

    private static SheetData.SheetDataBuilder getSheetDataBuilder() {
        return SheetData.builder().sheetId(SHEET_ID).sheetName(SHEET_NAME);
    }

    public static SheetData getSheetData() {
        return getSheetDataBuilder()
                .worksheets(asList(getWorksheetSubject(), getWorksheetSubjectToUnit()))
                .build();
    }

    public static SheetData getSheetDataWithEmptyWorksheets() {
        return getSheetDataBuilder()
                .worksheets(asList(
                        Worksheet.builder().sheetName(SUBJECT_WORKSHEET_NAME).columns(new HashMap<>()).rows(new ArrayList<>()).build(),
                        Worksheet.builder().sheetName(SUBJECT_TO_UNIT_WORKSHEET_NAME).columns(new HashMap<>()).rows(new ArrayList<>()).build()))
                .build();
    }

    public static SheetData getSheetDataWithEmptyRows() {
        return getSheetDataBuilder()
                .worksheets(asList(
                        Worksheet.builder().sheetName(SUBJECT_WORKSHEET_NAME).columns(getWorksheetSubjectColumns()).rows(new ArrayList<>()).build(),
                        Worksheet.builder().sheetName(SUBJECT_TO_UNIT_WORKSHEET_NAME).columns(getWorksheetSubjectColumns()).rows(new ArrayList<>()).build()))
                .build();
    }

    public static Worksheet getWorksheetSubject() {
        return Worksheet.builder()
                .sheetName(SUBJECT_WORKSHEET_NAME)
                .columns(getWorksheetSubjectColumns())
                .rows(getWorksheetSubjectRows())
                .build();
    }

    private static Map<String, Column> getWorksheetSubjectColumns() {
        Map<String, Column> columns = new HashMap<>();
        columns.put(SUBJECT_ID_COLUMN_NAME, Column.builder().id("A").name(SUBJECT_ID_COLUMN_NAME).build());
        columns.put("subject_id_type", Column.builder().id("B").name("subject_id_type").build());
        columns.put("subject_type", Column.builder().id("C").name("subject_type").build());
        columns.put("subject_title", Column.builder().id("D").name("subject_title").build());
        columns.put("question_type", Column.builder().id("E").name("question_type").build());
        columns.put("SPACE ACCELERATION", Column.builder().id("F").name("SPACE ACCELERATION").build());
        return columns;
    }


    private static List<Row> getWorksheetSubjectRows() {
        Map<String, String> row1Values = new HashMap<String, String>() {{
            put(SUBJECT_ID_COLUMN_NAME, "AAAAA");
            put("subject_id_type", "SCIENCE");
            put("subject_type", "PHYSICS");
            put("subject_title", "1");
        }};
        Row row1 = Row.builder()
                .id(SHEET_DATA_ROW_2)
                .values(row1Values)
                .build();

        Map<String, String> row2Values = new HashMap<String, String>() {{
            put(SUBJECT_ID_COLUMN_NAME, "BBBBB");
            put("subject_id_type", "SCIENCE");
            put("subject_type", "PHYSICS");
            put("subject_title", "2");
        }};
        Row row2 = Row.builder()
                .id(SHEET_DATA_ROW_3)
                .values(row2Values)
                .build();

        return asList(row1, row2);
    }

    public static SheetData getSheetDataForSubjectToUnitWorksheet() {
        return getSheetDataBuilder().worksheets(singletonList(getWorksheetSubjectToUnit())).build();
    }

    public static Worksheet getWorksheetSubjectToUnit() {
        return Worksheet.builder()
                .sheetName(SUBJECT_TO_UNIT_WORKSHEET_NAME)
                .columns(getWorksheetSubjectToUnitColumns())
                .rows(getWorksheetSubjectToUnitRows())
                .build();
    }


    private static Map<String, Column> getWorksheetSubjectToUnitColumns() {
        Map<String, Column> columns = new HashMap<>();
        columns.put(UNIT_ID_COLUMN_NAME, Column.builder().id("A").name(UNIT_ID_COLUMN_NAME).build());
        columns.put("unit_id_type", Column.builder().id("B").name("unit_id_type").build());
        columns.put("unit_type", Column.builder().id("C").name("unit_type").build());
        columns.put("unit", Column.builder().id("D").name("unit").build());
        columns.put("unit_relation", Column.builder().id("E").name("unit_relation").build());
        columns.put("unit_relation_type", Column.builder().id("F").name("unit_relation_type").build());
        columns.put(SUBJECT_ID_COLUMN_NAME, Column.builder().id("G").name(SUBJECT_ID_COLUMN_NAME).build());
        columns.put("subject_id_type", Column.builder().id("H").name("subject_id_type").build());
        columns.put("subject_title", Column.builder().id("I").name("subject_title").build());
        columns.put("subject_type", Column.builder().id("J").name("subject_type").build());
        columns.put("subject_usage", Column.builder().id("K").name("subject_usage").build());
        return columns;
    }


    private static List<Row> getWorksheetSubjectToUnitRows() {
        Map<String, String> row1Values = new HashMap<String, String>() {{
            put(UNIT_ID_COLUMN_NAME, "1111");
            put("unit_id_type", "Space");
            put("unit_type", "Rocket Science");
            put("unit", "1.1.1");
            put("unit_relation", "Associated to");
            put("unit_relation_type", "Correlates to");
            put(SUBJECT_ID_COLUMN_NAME, "AAAAA");
            put("subject_id_type", "SCIENCE");
            put("subject_title", "1");
            put("subject_type", "PHYSICS");
        }};
        Row row1 = Row.builder()
                .id(SHEET_DATA_ROW_2)
                .values(row1Values)
                .build();

        Map<String, String> row2Values = new HashMap<String, String>() {{
            put(UNIT_ID_COLUMN_NAME, "1111");
            put("unit_id_type", "Space");
            put("unit_type", "Rocket Science");
            put("unit", "1.1.1");
            put("unit_relation", "Associated to");
            put("unit_relation_type", "Correlates to");
            put(SUBJECT_ID_COLUMN_NAME, "BBBBB");
            put("subject_id_type", "SCIENCE");
            put("subject_title", "2");
            put("subject_type", "PHYSICS");
        }};
        Row row2 = Row.builder()
                .id(SHEET_DATA_ROW_3)
                .values(row2Values)
                .build();

        return asList(row1, row2);
    }

    public static SheetData getSheetDataForSubjectToUnitWorksheetUnitColumns() {
        return getSheetDataBuilder()
                .worksheets(singletonList(getWorksheetSubjectToUnitOnlyUnitColumns()))
                .build();
    }

    public static Worksheet getWorksheetSubjectToUnitOnlyUnitColumns() {
        return Worksheet.builder()
                .sheetName(SUBJECT_TO_UNIT_WORKSHEET_NAME)
                .columns(getSubjectToUnitOnlyUnitColumns())
                .rows(getSubjectToUnitRowsOnlyUnitColumns())
                .build();
    }


    private static Map<String, Column> getSubjectToUnitOnlyUnitColumns() {
        Map<String, Column> columns = new HashMap<>();
        columns.put("unit_id", Column.builder().id("A").name("unit_id").build());
        columns.put("unit_id_type", Column.builder().id("B").name("unit_id_type").build());
        columns.put("unit_type", Column.builder().id("C").name("unit_type").build());
        columns.put("unit", Column.builder().id("D").name("unit").build());
        return columns;
    }


    private static List<Row> getSubjectToUnitRowsOnlyUnitColumns() {
        Map<String, String> row1Values = new HashMap<String, String>() {{
            put(UNIT_ID_COLUMN_NAME, "1111");
            put("unit_id_type", "Space");
            put("unit_type", "Rocket Science");
            put("unit", "1.1.1");
        }};
        Row row1 = Row.builder()
                .id(SHEET_DATA_ROW_2)
                .values(row1Values)
                .build();

        Map<String, String> row2Values = new HashMap<String, String>() {{
            put(UNIT_ID_COLUMN_NAME, "1111");
            put("unit_id_type", "Space");
            put("unit_type", "Rocket Science");
            put("unit", "1.1.1");
        }};
        Row row2 = Row.builder()
                .id(SHEET_DATA_ROW_3)
                .values(row2Values)
                .build();

        return asList(row1, row2);
    }


    public static SheetData getSheetInfo() {
        return getSheetDataBuilder().build();
    }

}
