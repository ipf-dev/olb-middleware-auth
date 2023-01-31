package com.iportfolio.olbmiddlewareauth.repository

import com.iportfolio.olbmiddlewareauth.model.AuthCredentials
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository: CrudRepository<AuthCredentials, String> {
}