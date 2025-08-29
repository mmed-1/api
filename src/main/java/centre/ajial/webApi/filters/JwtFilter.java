package centre.ajial.webApi.filters;

import centre.ajial.webApi.services.JwtService;
import centre.ajial.webApi.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String param = request.getHeader("Authorization");
        String token = null, email = null;
        //test if token there correctly
        if (param != null && param.startsWith("Bearer ")) {
            token = param.substring(7);
            email = jwtService.extractUserName(token);
        }
        //validity
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context
                    .getBean(MyUserDetailsService.class)
                    .loadUserByUsername(email);
            //token valid?
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken tk =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                tk.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(tk);
            }
        }
        filterChain.doFilter(request, response);
    }
}
