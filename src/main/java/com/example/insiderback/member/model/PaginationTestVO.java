package com.example.insiderback.member.model;

import com.example.insiderback.common.model.CmmnPagingModel;
import lombok.Data;

@Data
public class PaginationTestVO extends CmmnPagingModel {
    private int count;
}