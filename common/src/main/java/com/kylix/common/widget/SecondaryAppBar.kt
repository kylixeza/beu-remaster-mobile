package com.kylix.common.widget

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
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
    showSearchView: Boolean = false,
    searchViewHint: String = "",
    onSearch: (query: String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    tvTitle.text = title
    if (showSearchView) tilSearch.show() else tilSearch.dispose()
    ivArrowBack.setOnClickListener { onBack() }

    edtSearch.hint = searchViewHint

    scope.lifecycleScope.launch(Dispatchers.Main) {
        edtSearch.textChanges().debounce(500).collect {
            onSearch(it.toString())
        }
    }
}