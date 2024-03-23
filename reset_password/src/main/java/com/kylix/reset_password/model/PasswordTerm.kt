package com.kylix.reset_password.model

data class PasswordTerm(
    val type: PasswordTermType,
    val description: String,
    val isFulfilled: Boolean = false,
)

enum class PasswordTermType {
    MINIMUM_8_CHARACTERS,
    ONE_WORD_ONE_CHARACTER,
    ONE_SPECIAL_CHARACTER,
}
