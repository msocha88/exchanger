package pl.exchanger.exchanger.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import pl.exchanger.exchanger.exceptions.WrongApiKeyException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter implements javax.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader("authorization");

        if (httpServletRequest == null || !header.startsWith("Bearer ")) {

            throw new ServletException("Wrong or empty Header");

        } else {

            try {

                String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("Jupikajej").parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);

            } catch (Exception ex) {

                throw new WrongApiKeyException();
            }
        }
        chain.doFilter(request,response);
    }
}
