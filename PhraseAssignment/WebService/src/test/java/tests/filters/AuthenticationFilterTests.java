package tests.filters;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webservice.filters.AuthenticationFilter;
import webservice.infrastructure.JwtDecoder;
import webservice.infrastructure.TokenProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationFilterTests {
    private AuthenticationFilter filter;
    @Mock
    private JwtDecoder jwtDecoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @Test
    public void authFilter_noAuthRequest_headerNotCheckedAndTokenNotSetAndNextInChainCalled() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/notProject/...");
        filter = new AuthenticationFilter(jwtDecoder, tokenProvider);

        filter.doFilter(request, response, chain);

        verify(request, never()).getHeader(any(String.class));
        verify(tokenProvider, never()).setToken(any(String.class));
        verify(chain).doFilter(request, response);
    }

    @Test
    public void authFilter_validHeader_tokenAcquiredAndNextInChainCalled() throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException, IOException, ServletException {
        when(jwtDecoder.decryptToken("jwt")).thenReturn("token");
        when(request.getHeader("Authorization")).thenReturn("Bearer jwt");
        when(request.getRequestURI()).thenReturn("/project/...");
        filter = new AuthenticationFilter(jwtDecoder, tokenProvider);

        filter.doFilter(request, response, chain);

        verify(tokenProvider).setToken("token");
        verify(chain).doFilter(request, response);
    }

    @Test
    public void authFilter_notBearerAuth_unauthorisedResponseStatus() throws IOException, ServletException {
        when(request.getHeader("Authorization")).thenReturn("notBearer jwt");
        when(request.getRequestURI()).thenReturn("/project/...");
        filter = new AuthenticationFilter(jwtDecoder, tokenProvider);

        filter.doFilter(request, response, chain);

        verify(response).setStatus(401);
        verify(tokenProvider, never()).setToken(any(String.class));
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void authFilter_invalidKey_unauthorisedResponseStatus() throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException, IOException, ServletException {
        when(jwtDecoder.decryptToken("jwt")).thenThrow(InvalidKeySpecException.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer jwt");
        when(request.getRequestURI()).thenReturn("/project/...");
        filter = new AuthenticationFilter(jwtDecoder, tokenProvider);

        filter.doFilter(request, response, chain);

        verify(response).sendError(401, null);
        verify(tokenProvider, never()).setToken(any(String.class));
        verify(chain, never()).doFilter(request, response);
    }
}
