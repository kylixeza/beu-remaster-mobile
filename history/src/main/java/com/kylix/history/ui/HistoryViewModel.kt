package com.kylix.history.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.History
import com.kylix.core.repositories.history.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
): BaseViewModel() {

    private val _histories = MutableStateFlow<List<History>>(emptyList())
    val histories = _histories.asStateFlow()

    fun getHistories() {
        onDataLoading()
        viewModelScope.launch {
            historyRepository.getHistories().fold(
                ifLeft = {
                    onDataError(it.message)
                },
                ifRight = {
                    _histories.value = it.data
                    onDataSuccess()
                }
            )
        }
    }

}