package webservice.infrastructure;

import org.springframework.stereotype.Service;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class RsaKeysProvider {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    RsaKeysProvider() {
        // generating new pair just for the purpose of this task
        // in real app we would read it from some vault.
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        generator.initialize(2048);
        var pair = generator.generateKeyPair();

        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public String getPublicKeyBase64() {
        return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
