package com.santa.service;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class CryptService {
	
	private static final String ENV_KEY_NAME = "MY_APP_KEY";
	
	private static String key = "CryptKey"; 
	
    // Retrieve key bytes from env -> system property -> fallback default
    public static byte[] getKeyBytes() {
        //String key = System.getenv(ENV_KEY_NAME);
        //if (key == null || key.isEmpty()) {
        //    key = System.getProperty(SYS_PROP_KEY_NAME);
        //}
        //if (key == null || key.isEmpty()) {
        //    // optional fallback — change or remove for stricter behaviour
        //    key = "default_key_change_me";
        //}
        return key.getBytes(StandardCharsets.UTF_8);
    }

    // XOR input bytes with repeating key bytes
    private static byte[] xorWithKey(byte[] input, byte[] key) {
        if (input == null) return null;
        byte[] out = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            out[i] = (byte) (input[i] ^ key[i % key.length]);
        }
        return out;
    }

    // Convert bytes to lowercase hex string (two chars per byte)
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    // Convert hex string (two chars per byte) to byte[]
    public static byte[] hexToBytes(String hex) {
        if (hex == null) return null;
        String s = hex.trim();
        if (s.length() == 0) return new byte[0];
        if (s.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string length.");
        }
        int len = s.length() / 2;
        byte[] out = new byte[len];
        for (int i = 0; i < len; i++) {
            int idx = i * 2;
            int hi = Character.digit(s.charAt(idx), 16);
            int lo = Character.digit(s.charAt(idx + 1), 16);
            if (hi == -1 || lo == -1) {
                throw new IllegalArgumentException("Invalid hex character in input.");
            }
            out[i] = (byte) ((hi << 4) + lo);
        }
        return out;
    }

    // Public: encrypt plaintext and return hex string
    public static String encryptToHex(String plain) {
        if (plain == null) return null;
        byte[] keyBytes = getKeyBytes();
        byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = xorWithKey(plainBytes, keyBytes);
        return bytesToHex(encrypted);
    }

    // Public: decrypt hex string and return plaintext
    public static String decryptFromHex(String hexEncrypted) {
        if (hexEncrypted == null) return null;
        byte[] keyBytes = getKeyBytes();
        byte[] encrypted = hexToBytes(hexEncrypted);
        byte[] decrypted = xorWithKey(encrypted, keyBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

}
