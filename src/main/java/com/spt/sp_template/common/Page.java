package com.spt.sp_template.common;

import lombok.Data;

import java.util.List;
@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
