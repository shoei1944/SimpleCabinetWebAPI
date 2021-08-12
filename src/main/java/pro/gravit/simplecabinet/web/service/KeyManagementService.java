package pro.gravit.simplecabinet.web.service;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class KeyManagementService {
    private final ECPublicKey publicKey;
    private final ECPrivateKey privateKey;

    public KeyManagementService(ECPublicKey publicKey, ECPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPublicKey getPublicKey() {
        return publicKey;
    }

    public ECPrivateKey getPrivateKey() {
        return privateKey;
    }
}
