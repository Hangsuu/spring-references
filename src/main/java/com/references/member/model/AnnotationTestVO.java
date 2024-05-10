package com.references.member.model;

import com.references.common.mybatis.MaskingField;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import static com.references.common.mybatis.MaskingField.MaskingType.ID;

@Data
public class AnnotationTestVO {
    @MaskingField(value= ID)
    String nameValue;
    int age;
}
