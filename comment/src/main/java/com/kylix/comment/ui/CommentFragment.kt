package com.kylix.comment.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kylix.comment.R
import com.kylix.comment.adapter.CommentAdapter
import com.kylix.comment.databinding.FragmentCommentBinding
import com.kylix.common.base.BaseBottomSheetDialogFragment
import com.kylix.common.util.hide
import com.kylix.common.util.initLinearVertical
import com.kylix.common.util.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentFragment : BaseBottomSheetDialogFragment<FragmentCommentBinding>() {

    override val viewModel by viewModel<CommentViewModel>()

    private val commentAdapter by lazy {
        CommentAdapter(
            onWantToReplyComment = { comment ->
                viewModel.setReplyCommentPreview(comment.commentId, comment.username)
            }
        )
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentCommentBinding {
        return FragmentCommentBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentCommentBinding.bind() {

        val recipeId = requireArguments().getString(RECIPE_ID).orEmpty()
        viewModel.setRecipeId(recipeId)
        viewModel.getComments()

        rvComment.apply {
            initLinearVertical(requireContext(), commentAdapter)

            val screenHeightDp = (resources.configuration.screenHeightDp * resources.displayMetrics.density).toInt()
            val heightScale = 0.55
            layoutParams.apply {
                height = (screenHeightDp * heightScale).toInt()
            }
        }

        bottomBarComment.ivSendComment.setOnClickListener {
            val comment = bottomBarComment.tilComment.editText?.text.toString()
            viewModel.postComment(comment)
        }
    }

    override fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.comments.collect {
                if (it.isNotEmpty()) {
                    binding?.tvAnyMessage?.hide()
                    commentAdapter.submitList(it)
                } else {
                    binding?.tvAnyMessage?.show()
                    binding?.tvAnyMessage?.text = resources.getString(R.string.no_comments_yet)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.replyCommentPreview.collect {
                if (it == null)
                    binding?.bottomBarComment?.tilComment?.hint = resources.getString(R.string.add_comment)
                else
                    binding?.bottomBarComment?.tilComment?.hint = resources.getString(R.string.reply_to, it.username)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect {
                binding?.apply {
                    if (it?.isLoading == true) pbComment.show() else pbComment.hide()
                    if (it?.isLoading == true) rvComment.hide() else rvComment.show()
                    if (it?.isError == true) tvAnyMessage.show() else tvAnyMessage.hide()
                    if (it?.isError == true) tvAnyMessage.text = it.errorMessage
                    if (it?.isSuccess == true) bottomBarComment.tilComment.editText?.text?.clear()
                }
            }
        }
    }

    companion object {
        private const val RECIPE_ID = "recipeId"

        fun newInstance(recipeId: String): CommentFragment {
            return CommentFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPE_ID, recipeId)
                }
            }
        }
    }


}