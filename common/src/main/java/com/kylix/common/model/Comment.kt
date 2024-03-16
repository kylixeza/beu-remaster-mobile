package com.kylix.common.model

data class Comment(
    val commentId: String,
    val avatar: String,
    val username: String,
    val comment: String,
    val time: String,

    val replies: List<ReplyComment>,
)

data class ReplyComment(
    val commentId: String,
    val avatar: String,
    val username: String,
    val comment: String,
    val time: String,
    val replyCommentId: String,
)