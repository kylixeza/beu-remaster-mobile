package com.kylix.help_center.ui

import com.kylix.common.base.BaseActivity
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.widget.bind
import com.kylix.common.widget.successSnackbar
import com.kylix.help_center.databinding.ActivityHelpCenterBinding
import com.kylix.help_center.validator.HelpCenterValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpCenterActivity : BaseActivity<ActivityHelpCenterBinding>() {

    override val viewModel by viewModel<HelpCenterViewModel>()

    override fun inflateViewBinding(): ActivityHelpCenterBinding {
        return ActivityHelpCenterBinding.inflate(layoutInflater)
    }

    override fun constraintValidator(): ConstraintValidator<ActivityHelpCenterBinding> {
        return HelpCenterValidator(this, viewModel.hasEmail)
    }

    override fun ActivityHelpCenterBinding.bind() {
        viewModel.checkEmail()
        helpCenterAppBar.bind(
            this@HelpCenterActivity,
            "Bantuan",
            onBack = { finish() }
        )

        btnSend.setOnClickListener {
            val message = tilMessage.editText?.text.toString()
            viewModel.postMessage(message)
        }
    }

    override fun onDataSuccessLoaded() {
        binding.root.successSnackbar("Pesan berhasil terkirim, silahkan cek email anda")
        binding.tilMessage.editText?.text?.clear()
    }
}