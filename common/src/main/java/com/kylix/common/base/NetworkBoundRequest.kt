package com.kylix.common.base

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.util.Resource

abstract class NetworkBoundRequest<RequestType> {

    private suspend fun run(): Either<Resource.Error<Unit>, Resource.Success<Unit>> {
        preRequest()
        return when (val remoteResponse = createCall()) {
            is NetworkResponse.Success -> {
                saveCallResult(remoteResponse.body.data)
                Either.Right(Resource.Success(Unit))
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

    protected open suspend fun preRequest(){}

    protected abstract suspend fun createCall(): NetworkResponse<BaseResponse<RequestType>, BaseResponse<Unit>>

    protected abstract suspend fun saveCallResult(data: RequestType)
}