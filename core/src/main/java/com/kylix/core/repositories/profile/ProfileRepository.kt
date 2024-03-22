package com.kylix.core.repositories.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import arrow.core.Either
import com.kylix.common.model.User
import com.kylix.common.util.Success
import com.kylix.common.util.Error

interface ProfileRepository {

    suspend fun greet(): Either<Error, Success<String>>
    suspend fun getProfile(): Either<Error, Success<User>>
    suspend fun updateProfile(
        context: Context,
        username: String,
        name: String,
        email: String,
        phoneNumber: String,
        avatar: Uri?
    ): Either<Error, Success<User>>
}