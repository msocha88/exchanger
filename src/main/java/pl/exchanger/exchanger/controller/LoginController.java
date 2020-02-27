package pl.exchanger.exchanger.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.exchanger.exchanger.jwt.User;

import java.util.Date;

@RestController
public class LoginController {


    @PostMapping("/logIn")
    public String getJwt(@RequestBody User user) {

        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS384, user.getPassword())
                .compact();
    }

}
