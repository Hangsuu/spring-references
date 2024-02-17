package com.example.insiderback.common.crypto;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class EncryptUtilTest {
    @Test
    public void encryptAndDecrypt() throws Exception {
        EncryptUtil encryptUtil = new EncryptUtil();
        String s1 = "testWord1";
        String s2 = "testWord2";

        String encryptedS1 = encryptUtil.encrypt(s1);
        String encryptedS2 = encryptUtil.encrypt(s2);
        log.info("encrypted S1  = {}", encryptedS1);
        log.info("encrypted S2  = {}", encryptedS2);

        Assertions.assertThat(encryptedS1).isNotEqualTo(encryptedS2);

        String decryptedS1 = encryptUtil.decrypt(encryptedS1);
        String decryptedS2 = encryptUtil.decrypt(encryptedS2);

        Assertions.assertThat(s1).isEqualTo(decryptedS1);
        Assertions.assertThat(s1).isNotEqualTo(decryptedS2);
    }

    @Test
    public void getHashEncryptSHA256() {

    }
}
