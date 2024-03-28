package pro.gravit.simplecabinet.web.configuration.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.service.KeyManagementService;

@Service
public class JwtParserProvider {
    @Autowired
    private KeyManagementService service;

    public JwtParser makeParser() {
        return Jwts.parser()
                .requireIssuer("SimpleCabinet")
                .verifyWith(service.getPublicKey())
                .build();
    }
}
