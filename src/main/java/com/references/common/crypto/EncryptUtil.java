package com.references.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtil {
    private static String alg = "AES/CBC/PKCS5Padding";
    private static String key = "a00aa00000aaaaaaafdfdfdf98989889";
    public static byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    // 양뱡함 암호화
    public String encrypt(String value) throws Exception{
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String decrypt(String value) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            // Base64로 디코딩 후 AES decrypt
            byte[] encryptedBytes = Base64.getDecoder().decode(value);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            
            // String으로 변환하여 반환
            return new String(decryptedBytes);
        } catch (NullPointerException e) {
            return null;
        }
    }

    // 단방향 암호화
    public String getHashEncryptSHA256 (String text) {
        try {
            // SHA-256 해시 계산
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = sha256.digest(text.getBytes());

            // Base64로 인코딩
            String base64Encoded = Base64.getEncoder().encodeToString(hashBytes);

            return base64Encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
