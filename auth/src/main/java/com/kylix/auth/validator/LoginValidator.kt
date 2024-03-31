package com.kylix.auth.validator

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kylix.auth.databinding.FragmentLoginBinding
import com.kylix.common.util.ConstraintValidator
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

class LoginValidator(
    private val lifecycleOwner: LifecycleOwner
): ConstraintValidator<FragmentLoginBinding> {
    override fun FragmentLoginBinding.validate() {

        val identifierValid = tilIdentifier.editText?.textChanges()
            ?.skipInitialValue()?.map { it.isNotEmpty() }

        val passwordValid = tilPassword.editText?.textChanges()
            ?.skipInitialValue()?.map { it.isNotEmpty() }

        lifecycleOwner.lifecycleScope.launch {
            identifierValid?.collect {
                tilIdentifier.error = if (it) null else "Tidak boleh kosong"
                tilIdentifier.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            passwordValid?.collect {
                tilPassword.error = if (it) null else "Password tidak boleh kosong"
                tilPassword.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            combine(
                identifierValid ?: return@launch, passwordValid ?: return@launch
            ) { identifier, password ->
                identifier && password
            }.collect {
                btnLogin.isEnabled = it
            }
        }
    }
}