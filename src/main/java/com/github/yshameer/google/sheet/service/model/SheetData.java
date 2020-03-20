package com.github.yshameer.google.sheet.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SheetData implements Serializable {
    private static final long serialVersionUID = 1618970264823998712L;

    @NotNull(message = "sheetId should not be blank")
    private String sheetId;
    private String sheetName;
    @NotNull(message = "worksheets should not be null")
    private List<Worksheet> worksheets;
}
