package com.kylix.change_profile.validator

import android.util.Patterns
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.kylix.change_profile.databinding.ActivityChangeProfileBinding
import com.kylix.common.util.ConstraintValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

class ChangeProfileValidator(
    private val lifecycleOwner: LifecycleOwner
): ConstraintValidator<ActivityChangeProfileBinding> {

    override fun ActivityChangeProfileBinding.validate() {

        val isEmailValid = tilEmail.baseValidation { Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty() }
        val isUsernameValid = tilUsername.baseValidation { it.isNotEmpty() }
        val isFullNameValid = tilFullName.baseValidation { it.isNotEmpty() }
        val isPhoneNumberValid = tilPhoneNumber.baseValidation { it.isNotEmpty() }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            isEmailValid?.collect { edtEmail.error = if (it) null else "Email tidak valid" }
        }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            isUsernameValid?.collect {
                edtUsername.error = if (it) null else "Username tidak boleh kosong"
            }
        }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            isFullNameValid?.collect {
                edtFullName.error = if (it) null else "Nama lengkap tidak boleh kosong"
            }
        }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            isPhoneNumberValid?.collect {
                edtPhoneNumber.error = if (it) null else "Nomor telepon tidak boleh kosong"
            }
        }

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) combine@{
            combine(
                isEmailValid ?: return@combine,
                isUsernameValid ?: return@combine,
                isFullNameValid ?: return@combine,
                isPhoneNumberValid ?: return@combine
            ) { email, username, fullName, phoneNumber ->
                email && username && fullName && phoneNumber
            }.collect {
                btnSave.isEnabled = it
            }
        }
    }

    private fun TextInputLayout.baseValidation(
        condition: (String) -> Boolean,
    )= editText?.textChanges()?.skipInitialValue()?.map { it.toString() }?.map {
        condition(it)
    }

}