package com.example.demo.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtility {
	
	@Value("${app.secret}")
	private String secret;
	
	//6. validate username in token and database, expDate
	public boolean validateToken(String token,String username) {
		String tokenUsername = getUsername(token);
		return(username.equals(tokenUsername) && !isTokenExpired(token));
		
	}
	
	//5. Validate Exp Date
	public boolean isTokenExpired(String token) {
	    Date expDate=getExpDate(token);
	    return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	//4.Read subject/username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	
	//3.Read Exp Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	//2.Read Claims
	public Claims getClaims(String token) {
		
		return Jwts.parser().setSigningKey(secret.getBytes())
			   .parseClaimsJws(token)
			   .getBody() ;
	}
	//1.Generate token
	public String generateToken(String subject) {
		
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("SakshiJain")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512,secret.getBytes())
				.compact();
	}
	

}
