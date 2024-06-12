package utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;
public class Format {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
    }

    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }

    public static String getCurrencyFormat(int num) {
        Locale vietnamese = new Locale("vi", "VN");
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietnamese);
        return defaultFormat.format(num * 1000);
    }

    /**
     * Return a {@link java.lang.String String} that represents the cipher text
     * encrypted by md5 algorithm.
     */
    public static String md5(String message) {
        String digest;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Format.getLogger(Format.class.getName());
            digest = "";
        }
        return digest;
    }
}