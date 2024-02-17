package com.kylix.onboard.ui

import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.OnBoardContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardViewModel: BaseViewModel() {
    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage = _isLastPage.asStateFlow()

    private val _isFirstPage = MutableStateFlow(true)

    private val _onBoardContent = MutableStateFlow<OnBoardContent?>(null)
    val onBoardContent = _onBoardContent.asStateFlow()

    init {
        getOnBoardContent(_page.value)
    }

    fun nextPage() {
        _page.value = _page.value.plus(1)
        getOnBoardContent(_page.value)
        checkPagePosition()
    }

    fun setPage(page: Int) {
        _page.value = page
        getOnBoardContent(_page.value)
        checkPagePosition()
    }

    private fun checkPagePosition() {
        when (_page.value) {
            1 -> _isFirstPage.value = true
            4 -> _isLastPage.value = true
            else -> {
                _isFirstPage.value = false
                _isLastPage.value = false
            }
        }
    }

    private fun getOnBoardContent(page: Int) = run {
        val contents = listOf(
            OnBoardContent(
                1,
                "Panduan Memasak",
                "Temukan tutorial memasak yang sesuai dengan kategori diinginkan",
                "onboard1.lottie"
            ),
            OnBoardContent(
                2,
                "Diskusi Pengguna",
                "Kamu dapat berbagi pengalaman memasakmu dengan pengguna lainnya ",
                "onboard2.lottie"
            ),
            OnBoardContent(
                3,
                "Pengenalan Gambar",
                "Kamu dapat mencari resep masakan dengan mengarahkan kamera ke suatu makanan",
                "onboard3.lottie"
            ),
        ).first { it.page == page }

        _onBoardContent.value = (contents)
    }
}