package com.kylix.common.base

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.util.Success
import com.kylix.common.util.Error

abstract class NetworkBoundRequest<RequestType> {

    suspend fun run(): Either<Error, Success<Unit>> {
        preRequest()
        return when (val remoteResponse = createCall()) {
            is NetworkResponse.Success -> {
                saveCallResult(remoteResponse.body.data ?: return Either.Left(Error("Data tidak ditemukan")))
                Either.Right(Success(Unit))
            }

            is NetworkResponse.ServerError -> {
                val response = remoteResponse.body?.message
                Either.Left(Error(response ?: "Terjadi kesalahan pada server"))
            }

            is NetworkResponse.NetworkError -> {
                Either.Left(Error("Tidak ada koneksi internet"))
            }
            is NetworkResponse.UnknownError -> {
                Either.Left(Error("Terjadi kesalahan yang tidak diketahui"))
            }
        }
    }

    protected open suspend fun preRequest(){}

    protected abstract suspend fun createCall(): NetworkResponse<BaseResponse<RequestType>, BaseResponse<Unit>>

    protected abstract suspend fun saveCallResult(data: RequestType)
}