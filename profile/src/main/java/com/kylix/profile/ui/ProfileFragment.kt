package com.kylix.profile.ui

import android.view.ViewGroup
import com.kylix.common.base.BaseFragment
import com.kylix.common.util.draw
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import com.kylix.profile.R
import com.kylix.profile.adapter.SectionAdapter
import com.kylix.profile.databinding.FragmentProfileBinding
import com.kylix.profile.model.Setting
import com.kylix.profile.navigation.ProfileNavigation
import com.kylix.profile.util.ProfileSetting
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel by viewModel<ProfileViewModel>()

    private val profileNavigation by inject<ProfileNavigation>()

    private val sectionAdapter by lazy { SectionAdapter(
        onItemSelected = { navigateToWhichSettings(it) }
    ) }

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

        btnLogout.setOnClickListener {
            viewModel.logout {
                profileNavigation.navigateToAuth(requireActivity())
            }
        }
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

    override fun onResume() {
        viewModel.getUser()
        super.onResume()
    }

    private fun navigateToWhichSettings(setting: Setting) {
        when(setting.setting) {
            ProfileSetting.CHANGE_PROFILE -> profileNavigation.navigateToChangeProfile(requireActivity())
            ProfileSetting.CHANGE_PASSWORD -> profileNavigation.navigateToResetPassword(requireActivity())
            ProfileSetting.HISTORY -> profileNavigation.navigateToHistory(requireActivity())
            ProfileSetting.FAVORITE -> profileNavigation.navigateToFavorite(requireActivity())
            ProfileSetting.PRIVACY_POLICY -> profileNavigation.navigateToPrivacyPolicy(requireActivity())
            ProfileSetting.TERMS_AND_CONDITIONS -> profileNavigation.navigateToTermsAndConditions(requireActivity())
            ProfileSetting.HELP -> profileNavigation.navigateToHelpCenter(requireActivity())
        }
    }

    override fun systemBarColor(): Int {
        return com.kylix.common.R.color.white
    }

}