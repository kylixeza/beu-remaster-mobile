package com.kylix.core.repositories

import com.kylix.core.data.local.preference.BeuDataStore

class AuthRepositoryImpl(
    private val dataStore: BeuDataStore
): AuthRepository {

    override suspend fun isPassOnBoard(): Boolean {
        return dataStore.getIsPassOnBoard()
    }

    override suspend fun isLoggedIn(): Boolean {
        return dataStore.getToken() != null
    }


}