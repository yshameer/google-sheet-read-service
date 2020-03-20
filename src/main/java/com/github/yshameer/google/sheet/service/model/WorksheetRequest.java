package com.github.yshameer.google.sheet.service.model;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WorksheetRequest implements Serializable {
    private static final long serialVersionUID = 8419458655442061378L;

    @NotNull(message = "sheetName for worksheet should be specified")
    private String sheetName;
    private List<String> columns;
}
