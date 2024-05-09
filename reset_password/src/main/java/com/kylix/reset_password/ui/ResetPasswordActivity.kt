package com.kylix.reset_password.ui

import androidx.lifecycle.lifecycleScope
import com.kylix.reset_password.adapter.PasswordTermAdapter
import com.kylix.reset_password.databinding.ActivityChangePasswordBinding
import com.kylix.reset_password.validator.ResetPasswordValidator
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.initLinearVertical
import com.kylix.common.widget.bind
import com.kylix.common.widget.buildSuccessDialog
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges

class ResetPasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    override val viewModel by viewModel<ResetPasswordViewModel>()

    private val passwordTermAdapter by lazy { PasswordTermAdapter() }

    override fun inflateViewBinding(): ActivityChangePasswordBinding {
        return ActivityChangePasswordBinding.inflate(layoutInflater)
    }

    override fun ActivityChangePasswordBinding.bind() {
        changePasswordAppBar.bind(
            scope = this@ResetPasswordActivity,
            title = "Ubah Password",
            onBack = { finish() }
        )
        btnConfirm.isEnabled = true

        rvPasswordTerms.initLinearVertical(this@ResetPasswordActivity, passwordTermAdapter)

        btnConfirm.setOnClickListener {
            viewModel.resetPassword(tilPassword.editText?.text.toString())
        }
    }

    override fun constraintValidator(): ConstraintValidator<ActivityChangePasswordBinding> {
        return ResetPasswordValidator(this, viewModel.isAllFullFilled)
    }

    override fun observeState() {
        super.observeState()

        viewModel.terms.observe {
            passwordTermAdapter.submitList(it)
        }

        lifecycleScope.launch {
            binding.tilPassword.editText?.textChanges()?.map { it.toString() }?.collect {
                viewModel.onPasswordChanged(it)
            }
        }
    }

    override fun onDataSuccessLoaded() {
        this.buildSuccessDialog(
            title = "Berhasil",
            message = "Password berhasil diubah",
            buttonText = "Tutup",
        ) { finish() }.show()
    }
}