package com.kylix.common.util

data class Success<T>(val data: T)
data class Error(val message: String)