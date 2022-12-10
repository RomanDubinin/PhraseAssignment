package webservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webservice.infrastructure.RsaKeysProvider;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private RsaKeysProvider rsaKeysProvider;

    public AuthenticationController(RsaKeysProvider rsaKeysProvider) {
        this.rsaKeysProvider = rsaKeysProvider;
    }

    @GetMapping(value = "/rsa/public")
    public String getRsaPublicKey() {
        return rsaKeysProvider.getPublicKeyBase64();
    }
}
