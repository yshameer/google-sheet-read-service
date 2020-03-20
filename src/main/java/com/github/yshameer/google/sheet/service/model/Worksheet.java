package com.github.yshameer.google.sheet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Worksheet implements Serializable {
    private static final long serialVersionUID = 3993639232649870370L;

    @NotNull(message = "sheetName for worksheet should be specified")
    private String sheetName;
    private Map<String, Column> columns;
    private List<Row> rows;
}
