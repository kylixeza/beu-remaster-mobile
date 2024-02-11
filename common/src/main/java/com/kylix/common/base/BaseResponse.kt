package com.kylix.common.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
	@field:SerializedName("status_code")
	val statusCode: Int = 0,
	val message: String = "",
	val count: Int = 0,
	val data: T,
)
