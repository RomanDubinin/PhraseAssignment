package webservice.filters;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import webservice.infrastructure.JwtDecoder;
import webservice.infrastructure.TokenProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {
    private final Logger logger = Logger.getLogger(String.valueOf(AuthenticationFilter.class));
    private final JwtDecoder jwtDecoder;
    private final TokenProvider tokenProvider;
    private final String urlFilter = "/project/.*";

    public AuthenticationFilter(JwtDecoder jwtDecoder, TokenProvider tokenProvider) {
        this.jwtDecoder = jwtDecoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;
        if (httpRequest.getRequestURI().matches(urlFilter)) {
            var authHeader = httpRequest.getHeader("Authorization").split(" ");
            var type = authHeader[0];
            var jwt = authHeader[1];
            if (!Objects.equals(type, "Bearer")) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String apiToken;
            try {
                apiToken = jwtDecoder.decryptToken(jwt);
            } catch (NoSuchAlgorithmException | ParseException | JOSEException | InvalidKeySpecException e) {
                logger.log(Level.SEVERE, e.getMessage());
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            tokenProvider.setToken(apiToken);
            chain.doFilter(request, response);
            tokenProvider.setToken(null);
        } else {
            chain.doFilter(request, response);
        }
    }
}
