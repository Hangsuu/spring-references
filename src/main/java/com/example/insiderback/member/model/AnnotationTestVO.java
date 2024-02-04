package com.example.insiderback.member.model;

import com.example.insiderback.common.mybatis.MaskingField;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import static com.example.insiderback.common.mybatis.MaskingField.MaskingType.ID;

@Data
@Alias("annotation")
public class AnnotationTestVO {
    @MaskingField(value= ID)
    String nameValue;
    int age;
}
