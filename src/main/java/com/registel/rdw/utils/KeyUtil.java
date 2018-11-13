/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author lider_desarrollador
 */
public class KeyUtil {
    
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue  = "1DK4HJY8E7XM0BC9".getBytes();
    
    public static Key generateKey() {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }        
    
    public static String encrypt(String s) {
        
        if (s == null || s == "")
            return null;
        
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(s.getBytes());
            
            Base64.Encoder encoder = Base64.getEncoder();            
            String encryptedString = encoder.encodeToString(encrypted);
            
            return encryptedString;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Longitud de cadena de entrada es incorrecta.");
        } catch (Exception e) {
            System.out.println(e);
        } return null;
    }
    
    public static String decrypt(String s) {
        
        if (s == null || s == "")
            return null;
        
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
                        
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decrypted = decoder.decode(s);
            String decryptedString = new String(cipher.doFinal(decrypted));
                       
            return decryptedString;
        
        } catch (IllegalArgumentException e) {
            System.err.println("Longitud de cadena de entrada es incorrecta.");
        } catch (Exception e) {
            System.out.println(e);
        } return null;
    }    
}
