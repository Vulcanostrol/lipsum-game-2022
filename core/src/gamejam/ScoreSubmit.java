package gamejam;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ScoreSubmit {
    public static void submitScore(String name, int score) {
        try {
            submitScore(name, score, "http://gerben-meijer.nl/gamejam2022/submit.php");
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

    public static boolean submitScore(String name, int score, String addr) throws IOException, NoSuchAlgorithmException {
        Map<String,String> arguments = new HashMap<>();
        arguments.put("name", name);
        arguments.put("score", String.format("%d", (int) score));
        arguments.put("hash", toHex(getSHA(String.format("%s%s%s", name, String.format("%d", score), "manySecureMuchSafeSalt"))));
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

        URL url = new URL(addr + "?" + new String(out));
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.connect();

        String outp = null;
        try(InputStream is = http.getInputStream()) {
            outp = new String(is.readAllBytes());
        }

        boolean success = outp.startsWith("400 OK");

        if (!success) {
            System.err.println("Error during score submission. Site returned: " + outp);
        }

        return success;
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }
}
