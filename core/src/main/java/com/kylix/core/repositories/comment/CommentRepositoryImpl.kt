package com.kylix.core.repositories.comment

import arrow.core.Either
import com.haroldadmin.cnradapter.NetworkResponse
import com.kylix.common.base.BaseResponse
import com.kylix.common.base.NetworkOnlyResource
import com.kylix.common.model.Comment
import com.kylix.common.util.Success
import com.kylix.common.util.Error
import com.kylix.core.data.api.comment.CommentApiService
import com.kylix.core.data.api.model.comment.CommentRequest
import com.kylix.core.data.api.model.comment.CommentResponse

class CommentRepositoryImpl(
    private val commentApiService: CommentApiService
): CommentRepository {
    override suspend fun postComment(
        recipeId: String,
        comment: String,
        replyCommentId: String?
    ): Either<Error, Success<Unit>> {
        return object : NetworkOnlyResource<Unit, String>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<String>, BaseResponse<Unit>> {
                val body = CommentRequest(comment, replyCommentId)
                return commentApiService.postComment(recipeId, body)
            }

            override fun String.mapTransform() {
                return
            }

        }.run()
    }

    override suspend fun getComments(recipeId: String): Either<Error, Success<List<Comment>>> {
        return object : NetworkOnlyResource<List<Comment>, List<CommentResponse>>() {
            override suspend fun createCall(): NetworkResponse<BaseResponse<List<CommentResponse>>, BaseResponse<Unit>> {
                return commentApiService.getComments(recipeId)
            }

            override fun List<CommentResponse>.mapTransform(): List<Comment> {
                return map { it.toComment() }
            }
        }.run()
    }
}