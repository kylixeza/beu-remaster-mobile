package com.kylix.auth.validator

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kylix.auth.databinding.FragmentRegisterBinding
import com.kylix.common.util.ConstraintValidator
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

class RegisterValidator(
    private val lifecycleOwner: LifecycleOwner
): ConstraintValidator<FragmentRegisterBinding> {
    override fun FragmentRegisterBinding.validate() {

        val usernameValid = tilUsername.editText?.textChanges()
            ?.skipInitialValue()
            ?.map {
                it.isNotEmpty() && !it.contains(" ") && !it.contains(Regex("[A-Z]"))
            }

        val fullNameValid = tilFullName.editText?.textChanges()
            ?.skipInitialValue()?.map { it.isNotEmpty() }

        val phoneNumberValid = tilPhoneNumber.editText?.textChanges()
            ?.skipInitialValue()?.map { it.isNotEmpty() }

        val passwordValid = tilPassword.editText?.textChanges()
            ?.skipInitialValue()?.map {
                it.length >= 8 && it.contains(Regex("[a-zA-Z]")) && it.contains(Regex("[0-9]")) && it.contains(Regex("^(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+\$"))
            }

        val confirmPasswordValid = tilConfirmPassword.editText?.textChanges()
            ?.skipInitialValue()?.map { it.toString() == tilPassword.editText?.text.toString() }

        lifecycleOwner.lifecycleScope.launch {
            tilUsername.editText?.textChanges()
                ?.skipInitialValue()?.map { it.toString() }
                ?.collect {
                    tilUsername.error = when {
                        it.contains(" ") -> "Tidak boleh ada spasi"
                        it.contains(Regex("[A-Z]")) ->  "Tidak boleh ada huruf besar"
                        it.isEmpty() ->"Tidak boleh kosong"
                        else ->null
                    }
                }
        }

        lifecycleOwner.lifecycleScope.launch {
            usernameValid?.collect {
                tilUsername.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            fullNameValid?.collect {
                tilFullName.error = if (it) null else "Tidak boleh kosong"
                tilFullName.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            phoneNumberValid?.collect {
                tilPhoneNumber.error = if (it) null else "Tidak boleh kosong"
                tilPhoneNumber.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            tilPassword.editText?.textChanges()
                ?.skipInitialValue()?.map { it.toString() }
                ?.collect {
                    tilPassword.error = when {
                        it.length < 8 -> "Minimal 8 karakter"
                        !it.contains(Regex("[a-zA-Z]")) -> "Minimal 1 huruf"
                        !it.contains(Regex("[0-9]")) -> "Minimal 1 angka"
                        !it.contains(Regex("^(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+\$")) -> "Minimal 1 karakter spesial"
                        it.isEmpty() -> "Tidak boleh kosong"
                        else -> null
                    }
                }
        }

        lifecycleOwner.lifecycleScope.launch {
            passwordValid?.collect {
                tilPassword.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            confirmPasswordValid?.collect {
                tilConfirmPassword.error = if (it) null else "Password tidak sama"
                tilConfirmPassword.isErrorEnabled = !it
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            combine(
                usernameValid ?: return@launch,
                fullNameValid ?: return@launch,
                phoneNumberValid ?: return@launch,
                passwordValid ?: return@launch,
                confirmPasswordValid ?: return@launch
            ) { username, fullName, phoneNumber, password, confirmPassword ->
                username && fullName && phoneNumber && password && confirmPassword
            }.collect {
                btnRegister.isEnabled = it
            }
        }
    }
}