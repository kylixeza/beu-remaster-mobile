package com.kylix.comment.ui

import androidx.lifecycle.viewModelScope
import com.kylix.common.base.BaseViewModel
import com.kylix.common.model.Comment
import com.kylix.core.repositories.comment.CommentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepository: CommentRepository
) : BaseViewModel() {

    private val _comments = MutableStateFlow(emptyList<Comment>())
    val comments = _comments.asStateFlow()

    private val _replyCommentPreview = MutableStateFlow<ReplyCommentPreview?>(null)
    val replyCommentPreview = _replyCommentPreview.asStateFlow()

    private var _recipeId = ""

    fun getComments() {
        onDataLoading()
        viewModelScope.launch {
            // Delay due to bugs in the backend :)
            delay(1000)
            commentRepository.getComments(_recipeId).fold(
                ifLeft = { onDataError(it.message) },
                ifRight = {
                    onDataSuccess()
                    _comments.value = it.data
                }
            )
        }
    }

    fun postComment(comment: String) {
        viewModelScope.launch {
            commentRepository.postComment(_recipeId, comment, _replyCommentPreview.value?.replyCommentId).fold(
                ifLeft = { onDataError(it.message) },
                ifRight = {
                    getComments()
                    clearReplyCommentPreview()
                }
            )
        }
    }

    fun setReplyCommentPreview(commentId: String, username: String) {
        _replyCommentPreview.value = ReplyCommentPreview(commentId, username)
    }

    private fun clearReplyCommentPreview() {
        _replyCommentPreview.value = null
    }

    fun setRecipeId(recipeId: String) {
        _recipeId = recipeId
    }
}

data class ReplyCommentPreview(
    val replyCommentId: String?,
    val username: String,
)