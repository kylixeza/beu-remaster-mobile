package com.kylix.common.base

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.util.Resource

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private suspend fun run(): Either<Resource.Error<Unit>, Resource.Success<ResultType>> {
        return when (val remoteResponse = createCall()) {
            is NetworkResponse.Success -> {
                val response = remoteResponse.body.data.mapTransform()
                Either.Right(Resource.Success(response))
            }

            is NetworkResponse.ServerError -> {
                val response = remoteResponse.body?.message
                Either.Left(Resource.Error(response ?: "Terjadi kesalahan pada server"))
            }

            is NetworkResponse.NetworkError -> {
                Either.Left(Resource.Error("Tidak ada koneksi internet"))
            }
            is NetworkResponse.UnknownError -> {
                Either.Left(Resource.Error("Terjadi kesalahan yang tidak diketahui"))
            }
        }
    }
    protected abstract suspend fun createCall(): NetworkResponse<BaseResponse<RequestType>, BaseResponse<Unit>>

    protected abstract fun RequestType.mapTransform(): ResultType
}