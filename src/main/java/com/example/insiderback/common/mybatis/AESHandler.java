package com.example.insiderback.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class AESHandler extends BaseTypeHandler {
    private static String alg = "AES/CBC/PKCS5Padding";
    private static String key = "a00aa00000aaaaaaafdfdfdf98989889";
    public static byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public String encrypt(String value) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String value) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            // Base64 디코딩을 수행한 후에 복호화를 진행(EncryptUtil의 decrypt()와 비교)
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            // 복호화된 결과를 UTF-8 문자열로 변환하여 반환
            return new String(decrypted, "UTF-8");
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String encrypted = rs.getString(columnName);
            return encrypted == null ? null : decrypt(encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String encrypted = rs.getString(columnIndex);
            return encrypted == null ? null : decrypt(encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            String encrypted = encrypt(parameter.toString());
            ps.setString(i, encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String encrypted = rs.getString(columnName);
            return encrypted == null ? null : decrypt(encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String encrypted = rs.getString(columnIndex);
            return encrypted == null ? null : decrypt(encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String encrypted = cs.getString(columnIndex);
            return encrypted == null ? null : decrypt(encrypted);
        } catch (Exception e) {
            throw new SQLException("Error decrypting value", e);
        }
    }
}
