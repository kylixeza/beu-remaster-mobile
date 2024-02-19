package com.kylix.auth.ui.register

import android.view.ViewGroup
import com.kylix.auth.databinding.FragmentRegisterBinding
import com.kylix.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val viewModel by viewModel<RegisterViewModel>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater, container, false)
    }

    override fun FragmentRegisterBinding.bind() {
        tvAlreadyHaveAccounts.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() = RegisterFragment()
    }

}