package pl.exchanger.exchanger.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import pl.exchanger.exchanger.exceptions.WrongApiKeyException;
import pl.exchanger.exchanger.model.logs.Log;
import pl.exchanger.exchanger.repository.LogRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter implements javax.servlet.Filter {

    @Autowired
    LogRepository logRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String header = httpServletRequest.getHeader("authorization");


        if (httpServletRequest == null || header.startsWith("Bearer ")) {
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
