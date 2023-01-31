package com.iportfolio.olbmiddlewareauth.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class JwtUtil {

    fun generateJWT(secret: String, subject: String, payload: Map<String, Any>, expiresIn: Long): String {
        val now = System.currentTimeMillis()
        val expiration = now + expiresIn

        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(Date(expiration))
            .addClaims(payload)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getPayload(secret: String, token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }
}
