package gamejam;

import gamejam.objects.collidable.explosion.Explosion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ScoreSubmit {
    public static void main(String[] args) {
        submitScore("Banaan", 10);
    }

    public static void submitScore(String name, float score) {
        try {
            submitScore(name, score, "http://10.53.109.189/submit.php");
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

    public static void submitScore(String name, float score, String addr) throws IOException, NoSuchAlgorithmException {
        Map<String,String> arguments = new HashMap<>();
        arguments.put("name", name);
        arguments.put("score", String.format("%f", score));
        arguments.put("hash", new String(getSHA(String.format("%s%s%s", name, String.format("%f", score), "manySecureMuchSafeSalt"))));
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        URL url = new URL(addr);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }

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
}
