package webservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;


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
        var jwt = EncryptedJWT.parse(jwtString);

        var decrypter = new RSADecrypter(rsaKeysProvider.getPrivateKey());
        jwt.decrypt(decrypter);
        var loginResponseJson = jwt.getJWTClaimsSet().getClaim("login_response").toString();

        LoginResponse loginResponse;
        try {
            loginResponse = objectMapper.readValue(loginResponseJson, LoginResponse.class);
        } catch (com.fasterxml.jackson.databind.JsonMappingException | com.fasterxml.jackson.core.JsonParseException e) {
            throw new IllegalArgumentException("JSON content of your JWT is invalid");
        }
        return loginResponse.getToken();
    }
}
