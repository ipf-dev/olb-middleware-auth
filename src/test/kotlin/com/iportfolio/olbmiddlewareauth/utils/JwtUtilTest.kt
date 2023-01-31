package com.iportfolio.olbmiddlewareauth.utils

import org.junit.Test
import java.util.*
import io.jsonwebtoken.*
import org.junit.Assert.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtUtilTest {

    @Test
    fun `유효한 JWT를 반환한다`() {
        val secret = "secret"
        val subject = "subject"
        val payload = mapOf("key" to "value")
        val expiresIn = 1000L
        val jwt = JwtUtil().generateJWT(secret, subject, payload, expiresIn)

        val claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(jwt)
            .body

        assertEquals(subject, claims.subject)
        assertEquals("value", claims["key"])

        assertTrue(claims.expiration.before(Date(System.currentTimeMillis() + expiresIn + 100)))
    }

    @Test
    fun `유효한 JWT로부터 Payload을 얻을 수 있다`() {
        val secret = "secret"
        val subject = "subject"
        val payload = mapOf("key" to "value")
        val expiresIn = 1000L
        val jwt = JwtUtil().generateJWT(secret, subject, payload, expiresIn)

        val result = JwtUtil().getPayload(secret, jwt)

        assertEquals(subject, result.subject)
        assertEquals("value", result["key"])
    }

    @Test(expected = IllegalArgumentException::class)
    fun `비밀키가 없다면 JWT 생성에 실패한다`() {
        val jwtUtil = JwtUtil()
        jwtUtil.generateJWT("", "subject", mapOf("key" to "value"), 100000)
    }

    @Test
    fun `잘 못 된 비밀키로 JWT 페이로드를 얻을 수 없다`() {
        val jwtUtil = JwtUtil()
        val secret = "secret"
        val subject = "subject"
        val payload = mapOf("key" to "value")
        val expiresIn = 3600L
        val token = jwtUtil.generateJWT(secret, subject, payload, expiresIn)
        val invalidSecret = "invalid secret"

        try {
            jwtUtil.getPayload(invalidSecret, token)
            fail("getPayload should fail with invalid secret")
        } catch (e: Exception) {
            assertTrue(e is SignatureException)
        }
    }
}
