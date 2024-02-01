package com.example.insiderback.member.model;

import com.example.insiderback.common.mybatis.MaskingField;
import lombok.Data;

import static com.example.insiderback.common.mybatis.MaskingField.MaskingType.ID;

@Data
public class AnnotationTestVO {
    @MaskingField(value= ID)
    String name;
    int age;
}
