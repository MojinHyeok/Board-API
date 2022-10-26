package board.Security.Util;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class SHA256Util {

    @SneakyThrows
    public static String encode(String text) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(text.getBytes());
        return bytesToHex(messageDigest.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
