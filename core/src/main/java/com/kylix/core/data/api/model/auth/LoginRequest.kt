package com.kylix.core.data.api.model.auth

data class LoginRequest(
    val identifier: String,
    val password: String
)
