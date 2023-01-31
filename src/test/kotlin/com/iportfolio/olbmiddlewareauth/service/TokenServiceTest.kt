package com.iportfolio.olbmiddlewareauth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.iportfolio.olbmiddlewareauth.model.AuthCredentials
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class TokenServiceTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, ByteArray>

    @Autowired
    private lateinit var tokenService: TokenService

    private val objectMapper = ObjectMapper()

    @Test
    fun `인증 정보를 Redis에 저장`() {
        val userId = "userId1"
        val authCredentials = AuthCredentials(userId, "accessToken1", "refreshToken1")

        tokenService.saveAuthCredentials(authCredentials)

        val storedAuthCredentials = redisTemplate.opsForValue().get(authCredentials.userId)
        assertNotNull(storedAuthCredentials)
    }

    @Test
    fun `User ID에 대한 인증 정보를 반환`() {
        val userId = "userId2"
        val authCredentials = AuthCredentials(userId, "accessToken2", "refreshToken2")
        redisTemplate.opsForValue().set(authCredentials.userId, objectMapper.writeValueAsBytes(authCredentials))

        val storedPerson = tokenService.getAuthCredentials(userId)
        assertEquals(authCredentials, storedPerson)
    }
    @Test
    fun `User Id로 토큰을 찾을 수 없다면 Null 반환`() {
        val result = tokenService.getAuthCredentials("Non-Existent Person")
        assertEquals(null, result)
    }
}
