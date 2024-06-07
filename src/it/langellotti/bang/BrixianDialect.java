package it.langellotti.bang;

public class BrixianDialect {
    public static String decrypt(String encrypted, String key) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < encrypted.length(); i++) {
            char decryptedChar = (char) (((encrypted.charAt(i) - key.charAt(i % key.length()) + 26) % 26) + 'a');
            decrypted.append(decryptedChar);
        }
        return decrypted.toString();
    }

    public static String encrypt(String plain, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < plain.length(); i++) {
            char encryptedChar = (char) (((plain.charAt(i) + key.charAt(i % key.length()) - 2 * 'a') % 26) + 'a');
            encrypted.append(encryptedChar);
        }
        return encrypted.toString();
    }
}
