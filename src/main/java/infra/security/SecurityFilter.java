import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    // ...

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            // Decode the JWT token
            Jwt jwt = jwtDecoder.decode(token);

            if (jwt != null) {
                String username = jwt.getSubject();

                if (username != null) {
                    // Token is valid
                    var usuario = usuarioRepository.findByEmail(username);
                    if (usuario != null) {
                        // Create authentication token
                        var authentication = new UsernamePasswordAuthenticationToken(
                                new DefaultOAuth2AuthenticatedPrincipal(jwt.getClaims(), jwt.getHeaders(), usuario.getAuthorities()),
                                token,
                                usuario.getAuthorities()
                        );

                        // Set authentication in security context
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);    // Call the next filter in the chain
    }
}
