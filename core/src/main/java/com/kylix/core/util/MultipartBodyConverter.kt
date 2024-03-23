package com.kylix.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

fun Bitmap.toRequestBody(): MultipartBody.Part {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
}

fun Uri.toMultipartBodyPart(context: Context): MultipartBody.Part? {
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

fun Uri.getMediaType(context: Context): MediaType {
    MimeTypeMap.getSingleton()
    val mimeType = context.contentResolver.getType(this)
    return mimeType?.toMediaTypeOrNull() ?: "image/jpeg".toMediaTypeOrNull()
    ?: throw IllegalArgumentException("Unsupported media type")
}