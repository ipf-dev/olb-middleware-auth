package com.iportfolio.olbmiddlewareauth.service

import com.iportfolio.olbmiddlewareauth.model.AuthCredentials
import com.iportfolio.olbmiddlewareauth.repository.TokenRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
class TokenServiceTest {

    @Mock
    lateinit var tokenRepository: TokenRepository

    @InjectMocks
    lateinit var tokenService: TokenService

    @Test
    fun `User ID 대한 인증 정보를 반환`() {
        val userId = "ipf"
        val authCredentials = AuthCredentials(userId, "accessToken1", "refreshToken1")

        `when`(tokenRepository.findById(userId)).thenReturn(Optional.of(authCredentials))

        val result = tokenService.getTokenByUserId(userId)

        assertEquals(authCredentials, result)
    }

    @Test
    fun `인증 정보를 Redis에 저장`() {
        val userId = "ipf"
        val authCredentials = AuthCredentials(userId, "accessToken1", "refreshToken1")
        `when`(tokenRepository.save(authCredentials)).thenReturn(authCredentials)

        val result = tokenService.setTokenWithUserId(authCredentials)

        assertEquals(authCredentials, result)
        verify(tokenRepository).save(authCredentials)
    }

    @Test(expected = IllegalStateException::class)
    fun `User ID로 토큰을 찾을 수 없는 경우 Exception 반환`() {
        val userId = "ipf"
        `when`(tokenRepository.findById(userId)).thenReturn(Optional.empty())

        tokenService.getTokenByUserId(userId)
    }
}
