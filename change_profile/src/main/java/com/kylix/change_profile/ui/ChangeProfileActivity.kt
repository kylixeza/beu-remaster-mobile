package com.kylix.change_profile.ui

import androidx.activity.result.contract.ActivityResultContracts
import com.kylix.change_profile.databinding.ActivityChangeProfileBinding
import com.kylix.change_profile.validator.ChangeProfileValidator
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.draw
import com.kylix.common.widget.bind
import com.kylix.common.widget.successSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProfileActivity : BaseActivity<ActivityChangeProfileBinding>() {

    override val viewModel by viewModel<ChangeProfileViewModel>()

    private val pickImageGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.setUri(uri)
    }

    override fun inflateViewBinding(): ActivityChangeProfileBinding {
        return ActivityChangeProfileBinding.inflate(layoutInflater)
    }

    override fun ActivityChangeProfileBinding.bind() {
        changeProfileAppBar.bind(
            scope = this@ChangeProfileActivity,
            "Ubah Profil",
            onBack = { finish() }
        )

        btnChangeProfilePicture.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnSave.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val name = binding.edtFullName.text.toString()
            val email = binding.edtEmail.text.toString()
            val phoneNumber = binding.edtPhoneNumber.text.toString()
            viewModel.updateUser(root.context, username, name, email, phoneNumber)
        }
    }

    override fun constraintValidator(): ConstraintValidator<ActivityChangeProfileBinding>? {
        return ChangeProfileValidator(this)
    }

    override fun observeState() {
        super.observeState()
        binding.apply {
            viewModel.user.observe {
                if (it == null) return@observe
                ivProfile.draw(this@ChangeProfileActivity, it.avatar) {
                    placeholder(com.kylix.common.R.drawable.ilu_default_profile_picture)
                        .circleCrop()
                }
                tilUsername.editText?.setText(it.username)
                tilFullName.editText?.setText(it.name)
                tilEmail.editText?.setText(it.email)
                tilPhoneNumber.editText?.setText(it.phoneNumber)
            }

            viewModel.uri.observe {
                if (it == null) return@observe
                ivProfile.draw(this@ChangeProfileActivity, it) {
                    circleCrop()
                }
            }
        }
    }

    override fun onDataSuccessLoaded() {
        binding.root.successSnackbar("Profil berhasil diubah")
        clearAllFocus()
    }

    private fun pickImageFromGallery() {
        pickImageGalleryLauncher.launch("image/*")
    }

    private fun clearAllFocus() {
        binding.apply {
            edtUsername.clearFocus()
            edtFullName.clearFocus()
            edtEmail.clearFocus()
            edtPhoneNumber.clearFocus()
        }
    }
}