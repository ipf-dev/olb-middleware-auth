package com.iportfolio.olbmiddlewareauth.repository

import com.iportfolio.olbmiddlewareauth.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TokenRepositoryTest @Autowired constructor(
    private val tokenRepository: TokenRepository
) {
}
