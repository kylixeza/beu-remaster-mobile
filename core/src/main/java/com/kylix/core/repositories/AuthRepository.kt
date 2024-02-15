package com.kylix.core.repositories

interface AuthRepository {

    suspend fun isPassOnBoard(): Boolean
    suspend fun isLoggedIn(): Boolean
}