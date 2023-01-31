package com.iportfolio.olbmiddlewareauth.service

import com.iportfolio.olbmiddlewareauth.model.AuthCredentials
import com.iportfolio.olbmiddlewareauth.repository.TokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TokenService(
    val repository: TokenRepository
) {

    fun getTokenByUserId(userId: String): AuthCredentials = repository.findByIdOrNull(userId) ?: throw IllegalStateException("No token found")

    fun setTokenWithUserId(authCredentials: AuthCredentials): AuthCredentials = repository.save(authCredentials)
}
