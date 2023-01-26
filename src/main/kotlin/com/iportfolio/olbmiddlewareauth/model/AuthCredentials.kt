package com.iportfolio.olbmiddlewareauth.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("AuthCredentials")
data class AuthCredentials(
    @Id
    var userId: String,
    var accessToken: String,
    val refreshToken: String,
)
