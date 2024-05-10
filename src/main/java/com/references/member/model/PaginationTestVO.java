package com.references.member.model;

import com.references.common.model.CmmnPagingModel;
import lombok.Data;

@Data
public class PaginationTestVO extends CmmnPagingModel {
    private int count;
}