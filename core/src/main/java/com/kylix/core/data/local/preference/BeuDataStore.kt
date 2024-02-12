package com.kylix.core.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kylix.core.data.local.preference.DataStoreUtil.DATA_STORE_NAME
import com.kylix.core.data.local.preference.DataStoreUtil.IS_FIRST_OPEN_PREF_KEY
import com.kylix.core.data.local.preference.DataStoreUtil.TOKEN_PREF_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BeuDataStore(
    private val context: Context
) {

    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun saveToken(token: String) {
        context.userPreferenceDataStore.edit {
            it[TOKEN_PREF_KEY] = token
        }
    }

    suspend fun getToken(): String? = context.userPreferenceDataStore.data
        .map { it[TOKEN_PREF_KEY] }.first()

    suspend fun deleteToken() {
        context.userPreferenceDataStore.edit {
            it.remove(TOKEN_PREF_KEY)
        }
    }

    suspend fun saveIsFirstTime(isFirstTime: Boolean) {
        context.userPreferenceDataStore.edit {
            it[IS_FIRST_OPEN_PREF_KEY] = isFirstTime
        }
    }

    suspend fun getIsFirstTime(): Boolean = context.userPreferenceDataStore.data
        .map { it[IS_FIRST_OPEN_PREF_KEY] ?: false }.first()
}