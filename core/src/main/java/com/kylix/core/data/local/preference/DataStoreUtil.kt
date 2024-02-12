package com.kylix.core.data.local.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreUtil {
    const val DATA_STORE_NAME = "beu_preference"
    val TOKEN_PREF_KEY = stringPreferencesKey("token")
    val IS_FIRST_OPEN_PREF_KEY = booleanPreferencesKey("is_first_open")
}