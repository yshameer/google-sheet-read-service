package com.github.yshameer.google.sheet.service.api;

import com.github.yshameer.google.sheet.service.helper.SheetsHelper;
import com.github.yshameer.google.sheet.service.model.SheetData;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.junit.Before;
import org.junit.Test;

import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.SUBJECT_TO_UNIT_WORKSHEET_NAME;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.SUBJECT_WORKSHEET_NAME;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.SHEET_ID;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getGoogleSpreadsheetExample;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetData;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetDataForSubjectToUnitWorksheet;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetDataForSubjectToUnitWorksheetUnitColumns;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetDataWithEmptyRows;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetDataWithEmptyWorksheets;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetInfo;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetRequestForSubjectToUnitWorksheet;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetRequestForSubjectToUnitWorksheetAndUnitColumns;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetRequestWithIdOnly;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetsHelperResponseWithSubjectData;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetsHelperResponseWithSubjectToUnitData;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetsHelperResponseWithEmptyColumns;
import static com.github.yshameer.google.sheet.service.api.mockdata.SpreadsheetMockData.getSheetsHelperResponseWithEmptyRows;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SheetApiControllerTest {

    private SheetsHelper sheetsHelper = mock(SheetsHelper.class);

    private SheetApiController sheetApiController = new SheetApiController(sheetsHelper);

    private Spreadsheet spreadsheet = getGoogleSpreadsheetExample();

    @Before
    public void before() {
        when(sheetsHelper.getSpreadsheet(SHEET_ID))
                .thenReturn(spreadsheet);
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithSubjectData());
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_TO_UNIT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithSubjectToUnitData());
    }

    @Test
    public void shouldGetSheetName() {
        SheetData expected = getSheetInfo();
        SheetData actual = sheetApiController.getSheetInfo(SHEET_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSheetDataForAllWorksheets() {
        SheetData expected = getSheetData();
        SheetData actual = sheetApiController.getSheetData(getSheetRequestWithIdOnly());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyRowsWhenRowDataIsEmpty() {
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithEmptyRows());
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_TO_UNIT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithEmptyRows());

        SheetData expected = getSheetDataWithEmptyRows();
        SheetData actual = sheetApiController.getSheetData(getSheetRequestWithIdOnly());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyRowsWhenColumnsIsEmpty() {
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithEmptyColumns());
        when(sheetsHelper.getData(SHEET_ID, SUBJECT_TO_UNIT_WORKSHEET_NAME))
                .thenReturn(getSheetsHelperResponseWithEmptyColumns());

        SheetData expected = getSheetDataWithEmptyWorksheets();
        SheetData actual = sheetApiController.getSheetData(getSheetRequestWithIdOnly());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSheetDataFromSpecificWorksheet() {
        SheetData expected = getSheetDataForSubjectToUnitWorksheet();
        SheetData actual = sheetApiController.getSheetData(getSheetRequestForSubjectToUnitWorksheet());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetSheetDataFromSpecificColumnsOfSpecificWorksheet() {
        SheetData expected = getSheetDataForSubjectToUnitWorksheetUnitColumns();
        SheetData actual = sheetApiController.getSheetData(getSheetRequestForSubjectToUnitWorksheetAndUnitColumns());
        assertEquals(expected, actual);
    }


}