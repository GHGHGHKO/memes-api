package me.synology.memesapi.common.config.security

import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.Date
import javax.servlet.http.HttpServletRequest

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import me.synology.memesapi.common.dto.jwt.TokenResponseDto
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService) {

    var secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun createToken(primaryKey: String, roles: MutableList<String>): TokenResponseDto {
        val claims = Jwts.claims().setSubject(primaryKey)
        claims["roles"] = roles // claims.put("roles", roles)
        val now = Date()
        val utcExpirationDate = Date(now.time + TOKEN_VALID_MILLISECOND)

        return TokenResponseDto(Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(utcExpirationDate)
            .signWith(secretKey)
            .compact(),
            utcExpirationDate,
            roles
        )
    }

    fun userPrimaryKey(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey).build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun userAuthentication(token: String): Authentication {
        val userDetails = userDetailsService
            .loadUserByUsername(userPrimaryKey(token))

        return UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.authorities)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(jwtToken)

            !claimsJws.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val TOKEN_VALID_MILLISECOND = 1000L * 60 * 60
    }
}
