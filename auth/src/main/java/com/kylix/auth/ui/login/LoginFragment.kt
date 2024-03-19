package com.kylix.auth.ui.login

import android.view.ViewGroup
import androidx.fragment.app.commit
import com.kylix.auth.R
import com.kylix.auth.databinding.FragmentLoginBinding
import com.kylix.auth.navigation.AuthNavigation
import com.kylix.auth.ui.register.RegisterFragment
import com.kylix.common.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel by viewModel<LoginViewModel>()

    private val navigation by inject<AuthNavigation>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentLoginBinding.bind() {
        tvDoNotHaveAccount.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.auth_container, RegisterFragment.newInstance(), RegisterFragment::class.java.simpleName)
                addToBackStack(null)
            }
        }

        btnLogin.setOnClickListener {
            val identifier = edtIdentifier.text.toString()
            val password = edtPassword.text.toString()

            viewModel.login(identifier, password)
        }
    }

    override fun onDataSuccessLoaded() {
        navigation.navigateToHome(requireActivity())
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}