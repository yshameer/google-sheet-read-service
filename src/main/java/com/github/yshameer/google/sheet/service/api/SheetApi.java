package com.github.yshameer.google.sheet.service.api;

import com.github.yshameer.google.sheet.service.model.SheetData;
import com.github.yshameer.google.sheet.service.model.SheetRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "Google Sheet Data", description = "Google Sheet Read API")
public interface SheetApi {

    @ApiOperation(value = "", notes = "Google Sheet Read API", tags = {"Google Sheet Data",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "sheet info returned successfully")})

    @RequestMapping(value = "/sheetInfo",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SheetData> sheetInfo(@ApiParam(value = "Sheet Id.", required = true) @RequestHeader(value = "sheetId", required = true) String sheetId);


    @ApiOperation(value = "", notes = "Google Sheet Read API", tags = {"Google Sheet Data",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "sheet info returned successfully")})

    @RequestMapping(value = "/sheetData",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SheetData> sheetData(@ApiParam(value = "sheetRequest") @RequestBody SheetRequest sheetRequest);


}
