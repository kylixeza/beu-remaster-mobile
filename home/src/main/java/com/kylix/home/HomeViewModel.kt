package com.kylix.home

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.Category
import com.kylix.core.repositories.category.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository
): BaseViewModel() {

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            onDataLoading()
            getCategories()
        }
    }

    private suspend fun getCategories() {
        categoryRepository.getCategories().fold(
            ifLeft = { error ->
                 onDataError(error.message)
            },
            ifRight = { success ->
                onDataSuccess()
                _categories.value = success.data
            }
        )
    }


}