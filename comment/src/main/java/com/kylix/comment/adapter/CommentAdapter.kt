package com.kylix.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kylix.comment.R
import com.kylix.comment.databinding.ItemCommentBinding
import com.kylix.common.base.BaseDiffUtil
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.Comment
import com.kylix.common.model.ReplyComment
import com.kylix.common.util.dispose
import com.kylix.common.util.draw
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.show

class CommentAdapter(
    private val onWantToReplyComment: (Comment) -> Unit = { _ -> },
): BaseRecyclerViewAdapter<ItemCommentBinding, Comment>() {

    private val expandableItems = mutableMapOf<Int, Boolean>()

    override fun inflateViewBinding(parent: ViewGroup): ItemCommentBinding {
        return ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun buildDiffUtilCallback(
        oldList: List<Comment>,
        newList: List<Comment>
    ): DiffUtil.Callback {
        return CommentDiffUtilCallback(oldList, newList)
    }

    override fun ItemCommentBinding.bindWithPosition(item: Comment, position: Int) {
        expandableItems[position] = false

        ivAvatar.draw(root.context, item.avatar) { placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture) }
        tvUsername.text = item.username
        tvComment.text = item.comment
        tvTime.text = item.time

        if (item.replies.isNotEmpty()) {
            tvReplyCount.show()
            tvReplyCount.text = root.context.getString(R.string.see_replies, item.replies.size)
        } else {
            tvReplyCount.dispose()
        }

        tvReplyCount.setOnClickListener { onReplyTextClicked(item.replies, position) }
        tvReply.setOnClickListener { onWantToReplyComment(item) }
    }

    private fun ItemCommentBinding.onReplyTextClicked(replies: List<ReplyComment>, position: Int) {

        if (expandableItems[position] == true) {
            //do close
            rvReply.dispose()
            tvReplyCount.text = root.context.getString(R.string.see_replies, replies.size)
            expandableItems[position] = false
        } else {
            //do open
            val replyCommentAdapter = ReplyCommentAdapter()
            rvReply.initLinearVertical(root.context, replyCommentAdapter)
            replyCommentAdapter.submitList(replies)
            rvReply.show()
            tvReplyCount.text = root.context.getString(R.string.close_comments)
            expandableItems[position] = true
        }
    }

    inner class CommentDiffUtilCallback(
        private val oldList: List<Comment>,
        private val newList: List<Comment>
    ): BaseDiffUtil<Comment, String>(oldList, newList) {
        override fun Comment.getItemIdentifier(): String {
            return this.commentId
        }

    }

}