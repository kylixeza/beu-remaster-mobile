package com.kylix.profile.ui

import android.view.ViewGroup
import com.kylix.common.base.BaseFragment
import com.kylix.common.base.BaseViewModel
import com.kylix.common.util.draw
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import com.kylix.profile.R
import com.kylix.profile.adapter.SectionAdapter
import com.kylix.profile.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel by viewModel<ProfileViewModel>()

    private val sectionAdapter by lazy { SectionAdapter() }

    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentProfileBinding.bind() {
        viewModel.getUser()
        profileAppBar.bind(
            scope = viewLifecycleOwner,
            title = resources.getString(R.string.profile_title),
            showBackButton = false
        )

        rvProfileSection.initLinearVertical(requireContext(), sectionAdapter)
    }

    override fun FragmentProfileBinding.onDataSuccessLoaded() {
        viewModel.user.observe {
            if (it == null) return@observe
            ivProfile.draw(requireContext(), it.avatar) {
                placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture)
                    .circleCrop()
            }
            tvName.text = it.name
            tvUsername.text = it.username
        }

        viewModel.profileSection.observe {
            sectionAdapter.submitList(it)
        }
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.white
    }

}