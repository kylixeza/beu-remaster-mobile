package com.kylix.core.repositories.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.MimeTypeMap
import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.User
import com.kylix.common.util.Error
import com.kylix.common.util.Success
import com.kylix.core.data.api.model.user.UserRequest
import com.kylix.core.data.api.model.user.UserResponse
import com.kylix.core.data.api.profile.ProfileApiService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService
) : ProfileRepository {

    override suspend fun greet(): Either<Error, Success<String>> {
        return object : NetworkOnlyResource<String, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                return profileApiService.greet()
            }

            override fun String.mapTransform(): String {
                return this
            }
        }.run()
    }

    override suspend fun getProfile(): Either<Error, Success<User>> {
        return object : NetworkOnlyResource<User, UserResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>> {
                return profileApiService.getProfile()
            }

            override fun UserResponse.mapTransform(): User {
                return this.toUser()
            }
        }.run()
    }

    override suspend fun updateProfile(
        context: Context,
        username: String,
        name: String,
        email: String,
        phoneNumber: String,
        avatar: Uri?
    ): Either<Error, Success<User>> {
        return object : NetworkOnlyResource<User, UserResponse>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<UserResponse>, BaseResponse<Unit>> {
                val body = UserRequest(
                    username = username,
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber
                )
                return profileApiService.updateProfile(body, avatar?.toMultipartBodyPart(context))
            }

            override fun UserResponse.mapTransform(): User {
                return this.toUser()
            }
        }.run()
    }

    private fun Uri.toMultipartBodyPart(context: Context): MultipartBody.Part? {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(this)
            inputStream?.let {
                val file = File(context.cacheDir, "temp_image_file")
                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val requestFile = file.asRequestBody(getMediaType(context))
                return MultipartBody.Part.createFormData("image", file.name, requestFile)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun Uri.getMediaType(context: Context): MediaType {
        MimeTypeMap.getSingleton()
        val mimeType = context.contentResolver.getType(this)
        return mimeType?.toMediaTypeOrNull() ?: "image/jpeg".toMediaTypeOrNull()
        ?: throw IllegalArgumentException("Unsupported media type")
    }
}