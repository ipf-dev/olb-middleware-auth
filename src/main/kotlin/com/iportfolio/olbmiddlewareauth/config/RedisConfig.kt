package com.iportfolio.olbmiddlewareauth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    // RedisTemplate을 통해 RedisConnection에서 넘겨준 byte 값을 객체 직렬화
    @Bean
    fun redisTemplate(): RedisTemplate<*, *>? {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
}
