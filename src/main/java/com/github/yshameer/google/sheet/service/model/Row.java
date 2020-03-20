package com.github.yshameer.google.sheet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Row implements Serializable {
    private static final long serialVersionUID = 4094309441900413569L;

    @NotNull(message = "row id should be specified")
    private Integer id;
    private Map<String, String> values;
}
