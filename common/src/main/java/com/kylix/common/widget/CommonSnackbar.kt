package com.kylix.common.widget

import android.view.View
import com.musfickjamil.snackify.Snackify

fun View.errorSnackbar(message: String) {
    Snackify.error(this, message, Snackify.LENGTH_SHORT).show()
}

fun View.successSnackbar(message: String) {
    Snackify.success(this, message, Snackify.LENGTH_SHORT).show()
}