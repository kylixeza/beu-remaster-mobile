package com.kylix.common.widget

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kylix.common.R
import com.kylix.common.databinding.SecondaryAppBarBinding
import com.kylix.common.util.dispose
import com.kylix.common.util.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

@OptIn(FlowPreview::class)
fun SecondaryAppBarBinding.bind(
    scope: LifecycleOwner,
    title: String,
    backgroundColor: Int = R.color.white,
    titleColor: Int = R.color.black,
    showBackButton: Boolean = true,
    backButtonColor: Int = R.color.black,
    showSearchView: Boolean = false,
    searchViewHint: String = "",
    onSearch: (query: String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    root.setBackgroundColor(root.context.getColor(backgroundColor))

    tvTitle.text = title
    tvTitle.setTextColor(root.context.getColor(titleColor))

    if (showSearchView) tilSearch.show() else tilSearch.dispose()
    if (showBackButton) ivArrowBack.show() else ivArrowBack.dispose()
    ivArrowBack.setOnClickListener { onBack() }
    ivArrowBack.imageTintList = root.context.getColorStateList(backButtonColor)

    edtSearch.hint = searchViewHint

    scope.lifecycleScope.launch(Dispatchers.Main) {
        edtSearch.textChanges().debounce(500).collect {
            onSearch(it.toString())
        }
    }
}