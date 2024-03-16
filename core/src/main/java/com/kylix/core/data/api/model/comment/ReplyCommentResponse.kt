package com.kylix.core.data.api.model.comment

import com.google.gson.annotations.SerializedName
import com.kylix.common.model.ReplyComment

data class ReplyCommentResponse(
    @field:SerializedName("comment_id")
    val commentId: String,
    val avatar: String,
    val username: String,
    val comment: String,
    val time: String,

    @field:SerializedName("reply_comment_id")
    val replyCommentId: String,
) {
    fun toReplyComment(): ReplyComment {
        return ReplyComment(
            commentId,
            avatar,
            username,
            comment,
            time,
            replyCommentId
        )
    }
}
