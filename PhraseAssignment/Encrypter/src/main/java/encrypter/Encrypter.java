package encrypter;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encrypter {
    public static void main(String[] args) {
        if (args.length != 2) {
            printHelp();
            return;
        }
        var content = args[0];
        var key = args[1];

        String jwtString;
        try {
            jwtString = encode(content, key);
        } catch (JOSEException | InvalidKeySpecException | NoSuchAlgorithmException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(jwtString);
    }

    private static String encode(String content, String key) throws JOSEException, InvalidKeySpecException, NoSuchAlgorithmException {
        var keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Key is not valid Base64 string");
        }
        var specPublic = new X509EncodedKeySpec(keyBytes);
        var rsaPub = (RSAPublicKey) (keyFactory.generatePublic(specPublic));

        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                .claim("login_response", content)
                .build();

        var header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        var jwt = new EncryptedJWT(header, jwtClaims);

        var encrypter = new RSAEncrypter(rsaPub);
        jwt.encrypt(encrypter);
        var jwtString = jwt.serialize();
        return jwtString;
    }

    private static void printHelp() {
        System.out.println("Aim of this program is just to help you encrypt content of login response from Phrase API (api2/v1/auth/login)");
        System.out.println("There are two positioned arguments:");

        System.out.println();
        System.out.println("Content \tthe login response from Phrase API");
        System.out.println("\t\tdon't forget to escape all quotes in it.");
        System.out.println("\t\tfor example it should be {\\\"user\\\":{\\\"username\\\":\\\"...");
        System.out.println("\t\tand not just {\"user\":{\"username\":\"...");

        System.out.println();
        System.out.println("Key \t\tpublic RSA key on which your content will be encoded");
        System.out.println("\t\tYou can acquire it from http://localhost:8080/authentication/rsa/public");
    }
}
