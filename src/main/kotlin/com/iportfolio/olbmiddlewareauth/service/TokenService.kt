package com.iportfolio.olbmiddlewareauth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.iportfolio.olbmiddlewareauth.model.AuthCredentials
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val redisTemplate: RedisTemplate<String, ByteArray>,
) {

    private val objectMapper = ObjectMapper()

    fun saveAuthCredentials(authCredentials: AuthCredentials) {
        redisTemplate.opsForValue().set(
            authCredentials.userId,
            objectMapper.writeValueAsBytes(authCredentials)
        )
    }

    fun getAuthCredentials(userId: String): AuthCredentials? {
        return objectMapper.readValue(
            redisTemplate.opsForValue().get(userId) ?: return null,
            AuthCredentials::class.java
        )
    }
}
