/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CLAIM_KEY_CREATED = "created";
	public static final String HEADER = "Authorization";
	public static final String BEARER = "Bearer ";
	public static final String SECRET = "e06649a153bbf788abfff4a5cddc1e06";
	public static final long REDIS_EXPIRATION = 3 * 24 * 60 * 60L;
	public static final long EXPIRATION = 365 * 24 * 60 * 60L;


	public String getUsernameFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims != null ? claims.getSubject() : null;
	}

	public Date getCreatedDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims != null ? new Date((Long) claims.get(CLAIM_KEY_CREATED)) : null;
	}

	public Date getExpirationDateFromToken(String token) {
		final Claims claims = getClaimsFromToken(token);
		return claims != null ? claims.getExpiration() : null;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>(2);
		claims.put(Claims.SUBJECT, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		final Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public String refreshToken(String token) {
		String refreshedToken = null;
		final Claims claims = getClaimsFromToken(token);
		if (claims != null) {
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		}
		return refreshedToken;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		return (userDetails.isEnabled() && username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(
				created, user.getLastPasswordResetDate()));
	}
}


