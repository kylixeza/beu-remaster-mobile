package com.kylix.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kylix.comment.databinding.ItemReplyCommentBinding
import com.kylix.common.base.BaseDiffUtil
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.model.ReplyComment
import com.kylix.common.util.draw

class ReplyCommentAdapter: BaseRecyclerViewAdapter<ItemReplyCommentBinding, ReplyComment>() {

    override fun inflateViewBinding(parent: ViewGroup): ItemReplyCommentBinding {
        return ItemReplyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun buildDiffUtilCallback(
        oldList: List<ReplyComment>,
        newList: List<ReplyComment>
    ): DiffUtil.Callback {
        return ReplyCommentDiffUtilCallback(oldList, newList)
    }

    override fun ItemReplyCommentBinding.bindWithPosition(item: ReplyComment, position: Int) {
        ivAvatar.draw(root.context, item.avatar) {
            placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture)
                .circleCrop()
        }
        tvUsername.text = item.username
        tvComment.text = item.comment
        tvTime.text = item.time
    }

    inner class ReplyCommentDiffUtilCallback(
        oldList: List<ReplyComment>,
        newList: List<ReplyComment>
    ): BaseDiffUtil<ReplyComment, String>(oldList, newList) {
        override fun ReplyComment.getItemIdentifier(): String {
            return commentId
        }
    }
}