package webservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;


@Service
public class JwtDecoder {
    private final ObjectMapper objectMapper;
    private final RsaKeysProvider rsaKeysProvider;

    public JwtDecoder(RsaKeysProvider rsaKeysProvider) {
        this.rsaKeysProvider = rsaKeysProvider;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public String decryptToken(String jwtString) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException, ParseException, JsonProcessingException {
        var keyFactory = KeyFactory.getInstance("RSA");
        var keyBytes = Base64.getDecoder().decode(rsaKeysProvider.getPrivateKey().getBytes(StandardCharsets.UTF_8));
        var specPrivate = new PKCS8EncodedKeySpec(keyBytes);
        var fileGeneratedPrivateKey = keyFactory.generatePrivate(specPrivate);
        var rsaPrv = (RSAPrivateKey) (fileGeneratedPrivateKey);

        var jwt = EncryptedJWT.parse(jwtString);
        var decrypter = new RSADecrypter(rsaPrv);
        jwt.decrypt(decrypter);
        var loginResponseJson = jwt.getJWTClaimsSet().getClaim("login_response").toString();

        var loginResponse = objectMapper.readValue(loginResponseJson, LoginResponse.class);
        return loginResponse.getToken();
    }
}
