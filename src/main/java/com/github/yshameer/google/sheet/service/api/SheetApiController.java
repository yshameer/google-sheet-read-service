package com.github.yshameer.google.sheet.service.api;

import com.github.yshameer.google.sheet.service.helper.SheetsHelper;
import com.github.yshameer.google.sheet.service.model.Column;
import com.github.yshameer.google.sheet.service.model.Row;
import com.github.yshameer.google.sheet.service.model.SheetData;
import com.github.yshameer.google.sheet.service.model.SheetRequest;
import com.github.yshameer.google.sheet.service.model.Worksheet;
import com.github.yshameer.google.sheet.service.model.WorksheetRequest;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.apache.commons.collections4.ListUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.github.yshameer.google.sheet.service.helper.SheetsHelper.DEFAULT_INDEX_SHIFT;
import static com.github.yshameer.google.sheet.service.helper.SheetsHelper.INDEX_ROW_WITH_COLUMN_NAMES;
import static com.github.yshameer.google.sheet.service.helper.SheetsHelper.INDEX_ROW_WITH_DATA_START;
import static com.github.yshameer.google.sheet.service.helper.SheetsHelper.columnIndexToLetterId;
import static java.util.stream.Collector.of;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.http.util.TextUtils.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Controller
public class SheetApiController implements SheetApi {

    private final SheetsHelper sheetsHelper;

    public SheetApiController(SheetsHelper sheetsHelper) {
        this.sheetsHelper = sheetsHelper;
    }


    @Override
    public ResponseEntity<SheetData> sheetInfo(String sheetId) {
        return new ResponseEntity<>(getSheetInfo(sheetId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SheetData> sheetData(SheetRequest sheetRequest) {
        return new ResponseEntity<>(getSheetData(sheetRequest), HttpStatus.OK);
    }


    public SheetData getSheetInfo(String sheetId) {
        Spreadsheet spreadsheet = sheetsHelper.getSpreadsheet(sheetId);
        String sheetName = spreadsheet.getProperties().getTitle();
        return SheetData.builder().sheetId(sheetId).sheetName(sheetName).build();
    }


    public SheetData getSheetData(SheetRequest sheetRequest) {
        String spreadsheetId = sheetRequest.getSheetId();

        Spreadsheet spreadsheet = sheetsHelper.getSpreadsheet(spreadsheetId);
        String spreadsheetName = spreadsheet.getProperties().getTitle();
        List<Worksheet> worksheets = getWorksheets(spreadsheet, sheetRequest.getSheets());

        return SheetData.builder()
                .sheetId(spreadsheetId)
                .sheetName(spreadsheetName)
                .worksheets(worksheets)
                .build();
    }

    private List<Worksheet> getWorksheets(Spreadsheet spreadsheet, List<WorksheetRequest> requestedWorksheets) {
        Map<String, List<String>> requestedData = mapRequestedWorksheetsData(requestedWorksheets);
        List<Sheet> sheets = spreadsheet.getSheets();
        return sheets.stream()
                .filter(sheet -> isRequestedSheet(requestedData, sheet))
                .map(sheet -> toWorksheet(spreadsheet.getSpreadsheetId(), sheet, requestedData))
                .collect(toList());
    }

    private Map<String, List<String>> mapRequestedWorksheetsData(List<WorksheetRequest> requestedWorksheets) {
        return emptyIfNull(requestedWorksheets).stream()
                .collect(groupingBy(WorksheetRequest::getSheetName,
                        mapping(request -> emptyIfNull(request.getColumns()),
                                of(ArrayList::new, List::addAll, ListUtils::union))));
    }

    private boolean isRequestedSheet(Map<String, List<String>> requestedData, Sheet sheet) {
        if (requestedData.isEmpty()) {
            return true;
        }
        return requestedData.containsKey(sheet.getProperties().getTitle());
    }

    private Worksheet toWorksheet(String spreadsheetId, Sheet sheet, Map<String, List<String>> requestedData) {
        String worksheetName = sheet.getProperties().getTitle();
        List<String> requestedColumns = requestedData.get(worksheetName);
        return toWorksheet(spreadsheetId, worksheetName, requestedColumns);
    }

    private Worksheet toWorksheet(String spreadsheetId, String worksheetName, List<String> requestedColumns) {
        List<List<String>> dataRows = sheetsHelper.getData(spreadsheetId, worksheetName);
        List<Row> rows = getRows(dataRows, requestedColumns);
        Map<String, Column> columns = getColumns(dataRows, requestedColumns);
        return Worksheet.builder()
                .sheetName(worksheetName)
                .rows(rows)
                .columns(columns)
                .build();
    }

    private Map<String, Column> getColumns(List<List<String>> dataRows, List<String> requestedColumns) {
        List<String> columns = dataRows.get(INDEX_ROW_WITH_COLUMN_NAMES);
        return IntStream.range(0, columns.size())
                .mapToObj(index -> toColumn(index, columns.get(index)))
                .filter(column -> isRequestedValue(column.getName(), requestedColumns))
                .collect(toMap(Column::getName, Function.identity()));
    }

    private Column toColumn(int index, String name) {
        return Column.builder()
                .name(name)
                .id(columnIndexToLetterId(index))
                .build();
    }

    private List<Row> getRows(List<List<String>> dataRows, List<String> requestedColumns) {
        List<String> columns = dataRows.get(INDEX_ROW_WITH_COLUMN_NAMES);
        return IntStream.range(INDEX_ROW_WITH_DATA_START, dataRows.size())
                .mapToObj(index -> toRow(index + DEFAULT_INDEX_SHIFT, dataRows.get(index), columns, requestedColumns))
                .filter(row -> !row.getValues().isEmpty())
                .collect(toList());
    }

    private Row toRow(Integer id, List<String> cells, List<String> columns, List<String> requestedColumns) {
        Map<String, String> values = toRowValues(cells, columns, requestedColumns);
        return Row.builder().id(id).values(values).build();
    }

    private Map<String, String> toRowValues(List<String> cells, List<String> columns, List<String> requestedColumns) {
        return IntStream.range(0, Integer.min(cells.size(), columns.size()))
                .boxed()
                .filter(index -> isRequestedValue(columns.get(index), requestedColumns))
                .filter(index -> isNotBlank(cells.get(index)))
                .collect(toConcurrentMap(columns::get, cells::get));
    }

    private boolean isRequestedValue(String column, List<String> requestedColumns) {
        if (isBlank(column)) {
            return false;
        }
        if (isEmpty(requestedColumns)) {
            return true;
        }
        return requestedColumns.contains(column);
    }


}
