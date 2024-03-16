package com.kylix.core.data.api.model.comment

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.Comment

data class CommentResponse(
    @field:SerializedName("comment_id")
    val commentId: String,
    val avatar: String,
    val username: String,
    val comment: String,
    val time: String,

    val replies: List<ReplyCommentResponse>,
) {
    fun toComment(): Comment {
        return Comment(
            commentId,
            avatar,
            username,
            comment,
            time,
            replies.map { it.toReplyComment() }
        )
    }
}