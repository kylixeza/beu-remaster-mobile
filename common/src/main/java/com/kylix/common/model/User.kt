package com.kylix.common.model

import com.google.gson.annotations.SerializedName

data class User(
    val username: String,
    val name: String,
    val avatar: String,
    val phoneNumber: String,
    val email: String?
)
