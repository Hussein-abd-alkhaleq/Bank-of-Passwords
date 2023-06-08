package com.example.bankofpasswords;

import android.content.Context;
import android.content.SharedPreferences;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class crypto {
    byte[] byte_plaintext;
    byte[] byte_ciphertext;
    byte[] byte_ciphertext1;
    byte[] byte_digest;


    public String encrypt_with_AES(String plaintext,String digest) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec=new SecretKeySpec(Base64.getDecoder().decode(digest),"AES");

            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            byte_ciphertext1 = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        }catch (Exception e){System.out.println(">>>encrypt>>>"+e.getMessage());}
        System.out.println(">>>encrypt value>>>"+Base64.getEncoder().encodeToString(byte_ciphertext1));
        return Base64.getEncoder().encodeToString(byte_ciphertext1);
    }

    public String encrypt_with_AES(Context context,String plaintext) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("login_password", 0);
            String login_password = preferences.getString("value", null);

            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec=new SecretKeySpec(Base64.getDecoder().decode(login_password),"AES");

            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            byte_ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        }catch (Exception e){System.out.println(">>>encrypt>>>"+e.getMessage());}
        System.out.println(">>>encrypt value>>>"+Base64.getEncoder().encodeToString(byte_ciphertext));
       return Base64.getEncoder().encodeToString(byte_ciphertext);
    }


    public String decrypt_with_AES(Context context, String ciphertext) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("login_password", 0);
            String login_password = preferences.getString("value", null);

            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec=new SecretKeySpec(Base64.getDecoder().decode(login_password),"AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte_plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));

        }catch (Exception e){System.out.println(">>>decrypt>>>"+e.getMessage());}

        return new String(byte_plaintext,StandardCharsets.UTF_8);
    }


    public String generate_SHA256_digest_string(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte_digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Base64.getEncoder().encodeToString(byte_digest));
        return Base64.getEncoder().encodeToString(byte_digest);
    }
}
