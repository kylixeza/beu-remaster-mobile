package com.kylix.common.util

import android.view.View

fun View.show() = run { visibility = View.VISIBLE }
fun View.hide() = run { visibility = View.INVISIBLE }
fun View.dispose() = run { visibility = View.GONE }

fun doNothing() = Unit