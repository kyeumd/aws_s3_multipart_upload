package aws.s3.auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SHA256Creator {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/kyeum_d/Documents/testVideo/test_aa";
        String filePath2 = "/Users/kyeum_d/Documents/testVideo/test_ab";
        String filePath3 = "/Users/kyeum_d/Documents/testVideo/test_ac";

        String checksumm = calculateSHA256Checksum(filePath);
        String checksumm2 = calculateSHA256Checksum(filePath2);
        String checksumm3 = calculateSHA256Checksum(filePath3);
        System.out.println("SHA-256 Checksum: " + checksumm);
        System.out.println("SHA-256 Checksum: " + checksumm2);
        System.out.println("SHA-256 Checksum: " + checksumm3);

        String checksum = calculateContentSHA256(filePath);
        String checksum2 = calculateContentSHA256(filePath2);
        String checksum3 = calculateContentSHA256(filePath3);
        System.out.println("SHA-256 Checksum: " + checksum);
        System.out.println("SHA-256 Checksum: " + checksum2);
        System.out.println("SHA-256 Checksum: " + checksum3);

        String checku = calculatePartHash(filePath);
        System.out.println(checku);

        String checkaa = calculatePartChecksum(filePath);
        System.out.println(checkaa);

    }

    public static String calculatePartChecksum(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(Files.readAllBytes(Paths.get(filePath)));
        byte[] base64EncodedHash = Base64.getEncoder().encode(hash);
        return new String(base64EncodedHash);
    }

    public static String calculatePartHash(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(Files.readAllBytes(Paths.get(filePath)));
        return bytesToHex(hash);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static String calculateContentSHA256(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(Files.readAllBytes(Paths.get(filePath)));
        return Base64.getEncoder().encodeToString(hash).toLowerCase();
    }

    public static String calculateSHA256Checksum(String filePath)
        throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        int bytesRead;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        byte[] hashBytes = digest.digest();

        StringBuilder hashHex = new StringBuilder();
        for (byte b : hashBytes) {
            hashHex.append(String.format("%02x", b));
        }

        return hashHex.toString();
    }


    public static String calculateHMACSHA256(String secretKey, String data)
        throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder hmacHex = new StringBuilder();
        for (byte b : hmacBytes) {
            hmacHex.append(String.format("%02x", b));
        }

        return hmacHex.toString();
    }
}
