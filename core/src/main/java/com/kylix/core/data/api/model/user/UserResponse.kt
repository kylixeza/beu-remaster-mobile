package com.kylix.core.data.api.model.user

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.User

data class UserResponse(
    val username: String,
    val name: String,
    val avatar: String,
    @field:SerializedName("phone_number")
    val phoneNumber: String,
    val email: String?
) {
    fun toUser() = User(
        username = username,
        name = name,
        avatar = avatar,
        phoneNumber = phoneNumber,
        email = email
    )
}
