package com.github.yshameer.google.sheet.service.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SheetRequest implements Serializable {
    private static final long serialVersionUID = 8882750521464601245L;

    @NotNull(message = "sheetId cannot be null")
    private String sheetId;
    private List<WorksheetRequest> sheets;
}
