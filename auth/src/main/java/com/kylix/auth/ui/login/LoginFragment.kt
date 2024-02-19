package com.kylix.auth.ui.login

import android.view.ViewGroup
import androidx.fragment.app.commit
import com.kylix.auth.R
import com.kylix.auth.databinding.FragmentLoginBinding
import com.kylix.auth.ui.register.RegisterFragment
import com.kylix.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel by viewModel<LoginViewModel>()

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
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}