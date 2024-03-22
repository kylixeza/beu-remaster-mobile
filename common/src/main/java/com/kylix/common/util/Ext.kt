package com.kylix.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

fun View.show() = run { visibility = View.VISIBLE }
fun View.hide() = run { visibility = View.INVISIBLE }
fun View.dispose() = run { visibility = View.GONE }

fun doNothing() = Unit

infix fun <T> T?.or(default: T): T = this ?: default

infix fun <T> T?.or(default: () -> T): T = this ?: default()

fun Int?.orZero() = this.or(0)

fun Int.orMinusOne() = this.or(-1)

fun ImageView.draw(drawable: Int) = setImageResource(drawable)

fun ImageView.draw(
    context: Context,
    url: String,
    requestBuilder: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable> = { this }
) {
    Glide.with(context)
        .load(url)
        .requestBuilder()
        .into(this)
}

fun ImageView.draw(
    context: Context,
    bitmap: Bitmap,
    requestBuilder: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable> = { this }
) {
    Glide.with(context)
        .load(bitmap)
        .requestBuilder()
        .into(this)
}

fun ImageView.draw(
    context: Context,
    uri: Uri,
    requestBuilder: RequestBuilder<Drawable>.() -> RequestBuilder<Drawable> = { this }
) {
    Glide.with(context)
        .load(uri)
        .requestBuilder()
        .into(this)
}

fun RecyclerView.initLinearVertical(context: Context, adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.setHasFixedSize(true)
}

fun RecyclerView.initLinearHorizontal(context: Context, adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    this.setHasFixedSize(true)
}

fun RecyclerView.initGridVertical(context: Context, adapter: RecyclerView.Adapter<*>, spanCount: Int) {
    this.adapter = adapter
    this.layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
}
