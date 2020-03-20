package com.github.yshameer.google.sheet.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class Column implements Serializable {
    private static final long serialVersionUID = 4094301541930413985L;

    private String id;
    private String name;
}
