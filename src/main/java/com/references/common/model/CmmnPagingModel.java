package com.references.common.model;

import lombok.Data;

@Data
public class CmmnPagingModel {

    private int currentPage;
    private int firstIndex;
    private int lastIndex;
    private int rnum;
}
