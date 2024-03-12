package com.kylix.common.widget

import com.kylix.common.databinding.BaseAppBarBinding
import com.kylix.common.util.hide
import com.kylix.common.util.show

fun BaseAppBarBinding.bind(
    title: String,
    showRightIcon: Boolean = false,
    onLeftIconPressed: () -> Unit = {},
    onRightIconPressed: () -> Unit = {}
) {
    tvTitle.text = title
    ivArrowBack.setOnClickListener { onLeftIconPressed() }
    if (showRightIcon) ivFavorite.show() else ivFavorite.hide()
    ivFavorite.setOnClickListener { onRightIconPressed() }
}