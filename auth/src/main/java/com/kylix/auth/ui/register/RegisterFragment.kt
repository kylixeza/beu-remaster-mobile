package com.kylix.auth.ui.register

import android.view.ViewGroup
import com.kylix.auth.databinding.FragmentRegisterBinding
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.common.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val viewModel by viewModel<RegisterViewModel>()

    private val navigation by inject<AuthNavigation>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentRegisterBinding.bind() {
        tvAlreadyHaveAccounts.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnRegister.setOnClickListener {
            viewModel.register(
                edtEmail.text.toString(),
                edtPassword.text.toString(),
                edtUsername.text.toString(),
                edtPhoneNumber.text.toString(),
                edtFullName.text.toString()
            )
        }
    }

    override fun FragmentRegisterBinding.onDataSuccessLoaded() {
        navigation.navigateToHome(requireActivity())
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

}