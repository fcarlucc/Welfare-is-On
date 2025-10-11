package com.apigateway.app.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.apigateway.app.exception.EmailNotFoundException;
import com.apigateway.app.exception.UserCannotAccessException;
import com.apigateway.app.model.User;
import com.apigateway.app.security.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service class for JWT (JSON Web Token) handling.
 */
@Service
public class JwtService {

	private static final String SECRET_KEY = "3cf7ff7e1f14e47d43bab57653a80512459106b707946a88098975a07ac381fd";

	private static final long jwtExpiration = 3 * 60 * 1000; // 3 minutes
//  private static final long jwtExpiration = 30 * 1000; // 30 seconds
//  private static final long jwtExpiration = 1440 * 60 * 1000; // 24 hours

	private static final long refreshExpiration = 1440 * 60 * 1000; // 24 hours
	private final UserService userService;

	/**
	 * Constructs a JwtService instance with a UserService dependency.
	 * @param userService UserService instance used for user-related operations.
	 */
	public JwtService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Extracts the username from the JWT.
	 * @param jwt String representing the JWT.
	 * @return String containing the extracted username.
	 */
	public String extractUsername(String jwt) {
		return extractClaim(jwt, Claims::getSubject);
	}

	/**
	 * Validates if the JWT is still valid for the given UserDetails.
	 * @param jwt String representing the JWT.
	 * @param user UserDetails instance representing the user details.
	 * @return true if the JWT is valid for the user, false otherwise.
	 */
	public boolean isTokenValid(String jwt, UserDetails user) {
		final String username = extractUsername(jwt);
		return (username.equals(user.getUsername())) && !isTokenExpired(jwt);
	}

	/**
	 * Checks if the JWT has expired.
	 * @param jwt String representing the JWT.
	 * @return true if the JWT has expired, false otherwise.
	 */
	public boolean isTokenExpired(String jwt) {
		return extractExpiration(jwt).before(new Date());
	}

	/**
	 * Extracts the expiration date from the JWT.
	 * @param jwt String representing the JWT.
	 * @return Date object representing the expiration date.
	 */
	public Date extractExpiration(String jwt) {
		return extractClaim(jwt, Claims::getExpiration);
	}

	/**
	 * Extracts a specific claim from the JWT using a claims resolver function.
	 * @param jwt String representing the JWT.
	 * @param claimsResolver Function to resolve specific claims from the JWT.
	 * @param <T> Type of the claim.
	 * @return Resolved claim value.
	 */
	public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwt);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the JWT and check the signature of the key.
	 * @param jwt String representing the JWT.
	 * @return Claims object containing all claims extracted from the JWT.
	 */
	public Claims extractAllClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt).getBody();
	}

	/**
	 * Retrieves the signing key used for JWT validation and generation.
	 * @return Key object representing the signing key.
	 */
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Generates a JWT for the provided UserDetails.
	 * @param userDetails MyUserDetails instance containing user details.
	 * @return String representing the generated JWT.
	 */
	public String generateToken(MyUserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	/**
	 * Generates a JWT with additional claims for the provided UserDetails.
	 * @param extraClaims Map containing additional claims to include in the JWT.
	 * @param userDetails MyUserDetails instance containing user details.
	 * @return String representing the generated JWT.
	 */
	public String generateToken(Map<String, Object> extraClaims, MyUserDetails userDetails) {
		extraClaims.put("userId", userDetails.getId());
		extraClaims.put("roles", userDetails.getAuthorities());
		extraClaims.put("fullName", userDetails.getFullName());
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	/**
	 * Generates a refresh token for the provided UserDetails.
	 * @param userDetails MyUserDetails instance containing user details.
	 * @return String representing the generated refresh token.
	 * @throws UserCannotAccessException if the user is blocked, expired, not verified, or does not exist.
	 */
	public String generateRefreshToken(MyUserDetails userDetails) {
		User user;
		try {
			user = userService.findByEmail(userDetails.getUsername());
		} catch (EmailNotFoundException e) {
			throw new UserCannotAccessException("User may be blocked, expired, not verified or not exist");
		}

		if (user.isBlocked() || user.isExpired() || !user.isEnabled()) {
			throw new UserCannotAccessException("User may be blocked, expired, not verified or not exist");
		}

		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}

	/**
	 * Builds a JWT with specified claims, UserDetails, and expiration.
	 * @param extraClaims Map containing additional claims to include in the JWT.
	 * @param userDetails MyUserDetails instance containing user details.
	 * @param expiration long representing the expiration time for the JWT.
	 * @return String representing the generated JWT.
	 */
	private String buildToken(Map<String, Object> extraClaims, MyUserDetails userDetails, long expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}
