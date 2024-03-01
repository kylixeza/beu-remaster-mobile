package com.kylix.common.base

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.util.Success
import com.kylix.common.util.Error

abstract class NetworkOnlyResource<ResultType, RequestType> {

    suspend fun run(): Either<Error, Success<ResultType>> {
        return when (val remoteResponse = createCall()) {
            is NetworkResponse.Success -> {
                val response = remoteResponse.body.data?.mapTransform() ?: return Either.Left(Error("Data tidak ditemukan"))
                Either.Right(Success(response))
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
    protected abstract suspend fun createCall(): NetworkResponse<BaseResponse<RequestType>, BaseResponse<Unit>>

    protected abstract fun RequestType.mapTransform(): ResultType
}