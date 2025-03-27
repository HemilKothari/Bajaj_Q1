import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper obj = new ObjectMapper();
            JsonNode root = obj.readTree(new File("input.json"));

            String firstName = root.path("Student").path("first_name").asText().toLowerCase();
            String rollNumber = root.path("Student").path("roll_number").asText().toLowerCase();

            String result = firstName + rollNumber;

            String md5Hash = generateMD5(result);

            FileWriter writer = new FileWriter("output.txt");
            writer.write(md5Hash);
            writer.close();

            System.out.println("MD5 Hash: " + md5Hash);
        } catch (IOException e) {
            System.err.println("Error reading json file: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error generating md5 Hash: " + e.getMessage());
        }
    }

    private static String generateMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b: digest) {
            hexString.append(String.format("%02x", b));
        } 

        return hexString.toString();
    }
}
