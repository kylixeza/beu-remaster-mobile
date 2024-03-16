package com.kylix.core.repositories.comment

import arrow.core.Either
import com.kylix.common.model.Comment
import com.kylix.common.util.Success
import com.kylix.common.util.Error

interface CommentRepository {

    suspend fun postComment(recipeId: String, comment: String, replyCommentId: String?): Either<Error, Success<Unit>>

    suspend fun getComments(recipeId: String): Either<Error, Success<List<Comment>>>

}