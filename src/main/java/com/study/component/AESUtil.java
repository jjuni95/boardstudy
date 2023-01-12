package com.study.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESUtil {

  private byte[] key;
  private SecretKeySpec secretKeySpec;

  @Autowired
  public AESUtil(@Value("${your secretkey path}") String rawKey) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      key = rawKey.getBytes(StandardCharsets.UTF_8);
      key = sha.digest(key);
      key = Arrays.copyOf(key, 24);
      secretKeySpec = new SecretKeySpec(key, "AES");
    } catch (Exception e) {
      //log.error(e.getMessage());
    }
  }

  public String encrypt(String str) {
    try {
      //알고리즘/블럭 암호화 방식/Padding방식(메세지 길이가 짧은 경우 어떻게 처리 할 것인가?)
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
      //cipher를 통한 암호화 결과 타입은 byte array이다. 이를 쉽게 다루기위해 base64 string 으로 encoding하여 사용한다.
      return encodeBase64(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      //log.error("Error while encrypt: " + e);
      return null;
    }
  }

  public String decrypt(String str) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

      return new String(cipher.doFinal(decodeBase64(str)));
    } catch (Exception e) {
      //log.error("Error while decrypt: " + e);
      return null;
    }
  }

  private String encodeBase64(byte[] source) {
    return Base64.getEncoder().encodeToString(source);
  }

  private byte[] decodeBase64(String encodedString) {
    return Base64.getDecoder().decode(encodedString);
  }
}