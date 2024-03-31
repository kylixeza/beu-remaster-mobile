package com.kylix.reset_password.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kylix.reset_password.R
import com.kylix.reset_password.databinding.ItemPasswordTemsBinding
import com.kylix.reset_password.model.PasswordTerm
import com.kylix.reset_password.model.PasswordTermType
import com.kylix.common.base.BaseRecyclerViewAdapter
import com.kylix.common.util.draw

class PasswordTermAdapter: BaseRecyclerViewAdapter<ItemPasswordTemsBinding, PasswordTerm>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemPasswordTemsBinding {
        return ItemPasswordTemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun ItemPasswordTemsBinding.bind(item: PasswordTerm) {
        ivSelect.draw(
            root.context,
            if (item.isFulfilled) R.drawable.ic_password_term_pass else R.drawable.ic_password_term_fail
        )
        tvPasswordTerm.setTextColor(
            root.context.resources.getColor(
                if (item.isFulfilled) com.kylix.common.R.color.primary_500 else com.kylix.common.R.color.neutral_400
            )
        )
        tvPasswordTerm.text = item.description
    }

    fun updateTerm(term: PasswordTermType) {
        val index = itemList.indexOfFirst { it.type == term }
        if (index != -1) {
            itemList[index] = itemList[index].copy(isFulfilled = true)
            notifyItemChanged(index)
        }
    }
}