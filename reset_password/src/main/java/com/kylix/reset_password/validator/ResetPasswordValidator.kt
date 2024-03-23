package com.kylix.reset_password.validator

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kylix.change_password.databinding.ActivityChangePasswordBinding
import com.kylix.common.util.ConstraintValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

class ResetPasswordValidator(
    private val lifecycleOwner: LifecycleOwner,
    private val additionalFlow: Flow<Boolean>
): ConstraintValidator<ActivityChangePasswordBinding> {
    override fun ActivityChangePasswordBinding.validate() {

        val passwordValue = tilPassword.editText?.textChanges()?.map { it.toString() }
        val confirmPasswordValue = tilConfirmPassword.editText?.textChanges()?.map { it.toString() }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            combine(
                passwordValue ?: return@launch,
                confirmPasswordValue ?: return@launch,
            ) { password, confirmPassword ->
                password != confirmPassword
            }.collect {
                tilConfirmPassword.error = if (it) "Password tidak sama" else null
            }
        }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            combine(
                passwordValue ?: return@launch,
                confirmPasswordValue ?: return@launch,
                additionalFlow
            ) { password, confirmPassword, additional ->
                (password == confirmPassword) && (password.isNotEmpty()) && (confirmPassword.isNotEmpty()) && additional
            }.collect {
                btnConfirm.isEnabled = it
            }
        }

    }
}