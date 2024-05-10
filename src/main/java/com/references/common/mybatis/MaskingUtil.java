package com.references.common.mybatis;

import com.references.common.crypto.EncryptField;
import com.references.common.crypto.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Component;

import javax.crypto.IllegalBlockSizeException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class MaskingUtil {

    public static <T> T maskingFieldProccess(T paramsObject) throws Exception {
        Class<? extends Object> resultClass = paramsObject.getClass();
        List<Field> declaredFieldList = FieldUtils.getAllFieldsList(resultClass);
        for (Field field : declaredFieldList) {
            EncryptField usedEncryptField = field.getAnnotation(EncryptField.class);
            MaskingField usedMaskingField = field.getAnnotation(MaskingField.class);
            if (!Objects.isNull(usedEncryptField)) {
                encryptField(field, paramsObject);
            }
            if (!Objects.isNull(usedMaskingField)) {
                maskingField(field, paramsObject);
            }
        }
        return paramsObject;
    }

    private static <T> void encryptField(Field field, T paramsObject) throws Exception {
        field.setAccessible(true);
        Object object = field.get(paramsObject);
        if (object instanceof String) {
            String value = (String) object;
            EncryptUtil encryptUtil = new EncryptUtil();
            try {
                field.set(paramsObject, encryptUtil.decrypt(value));
//            } catch (IllegalBlockSizeException e) {
//                field.set(paramsObject, encryptUtil.encrypt(value));
            } catch (IllegalArgumentException e) {
                field.set(paramsObject, encryptUtil.encrypt(value));
            } catch (Exception e) {
                field.set(paramsObject, null);
            }
        }
    }

    private static <T> void maskingField(Field field, T paramsObject) throws IllegalAccessException {
        field.setAccessible(true);
        Object object = field.get(paramsObject);
        if (object instanceof String) {
            String value = (String) object;
            switch (field.getAnnotation(MaskingField.class).value()) {
                case ID:
                    field.set(paramsObject, getIdMasking(value));
                    break;
            }
        }
    }

    public static String getIdMasking(String str) {
        String regex = "^[a-zA-Z0-9]*$";

        Matcher matcher = Pattern.compile(regex).matcher(str);
        if (matcher.find()) {
            String target = str;
            int length = str.length();
            int mskLen = 0;

            if (length > 3) {
                mskLen = 3;
            } else if (length > 2) {
                mskLen = 2;
            } else {
                mskLen = 1;
            }
            char[] c = new char[length - mskLen];
            Arrays.fill(c, '*');

            return str.replace(target, target.substring(0, mskLen) + String.valueOf(c));
        }
        return str;
    }

}
