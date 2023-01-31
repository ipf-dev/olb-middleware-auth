package com.iportfolio.olbmiddlewareauth.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("AuthCredentials")
data class AuthCredentials @JsonCreator constructor(
    @Id
    @JsonProperty("userId") val userId: String,
    @JsonProperty("accessToken") val accessToken: String,
    @JsonProperty("refreshToken") val refreshToken: String,
)
