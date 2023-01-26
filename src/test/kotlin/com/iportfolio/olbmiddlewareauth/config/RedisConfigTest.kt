package com.iportfolio.olbmiddlewareauth.config

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("\${spring.profiles.active}")
class RedisConfigTest {

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Test
    fun `Redis 연동 테스트`() {
        val ping = redisTemplate.execute { it.ping() }
        assert("PONG" == ping)
    }
}
