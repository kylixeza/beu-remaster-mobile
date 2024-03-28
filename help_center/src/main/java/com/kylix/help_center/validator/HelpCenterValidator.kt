package com.kylix.help_center.validator

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kylix.common.util.ConstraintValidator
import com.kylix.help_center.databinding.ActivityHelpCenterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges

class HelpCenterValidator(
    private val lifecycleOwner: LifecycleOwner,
    private val otherFlow: Flow<Boolean>
): ConstraintValidator<ActivityHelpCenterBinding> {
    override fun ActivityHelpCenterBinding.validate() {

        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            tilMessage.editText?.textChanges()?.combine(otherFlow) { message, hasEmail -> message to hasEmail }?.collect {
                val empty = it.first.isEmpty()
                val moreThanLength = it.first.length > tilMessage.counterMaxLength
                if (empty) tilMessage.error = "Pesan tidak boleh kosong" else tilMessage.error = null
                if (moreThanLength) tilMessage.error = "Pesan tidak boleh lebih dari ${tilMessage.counterMaxLength} karakter" else tilMessage.error = null

                val haveEmail = it.second
                if (!haveEmail) tilMessage.error = "Anda belum memiliki email, silahkan lengkapi profil anda"
                else tilMessage.error = null

                btnSend.isEnabled = !empty && !moreThanLength && haveEmail
            }
        }

    }
}