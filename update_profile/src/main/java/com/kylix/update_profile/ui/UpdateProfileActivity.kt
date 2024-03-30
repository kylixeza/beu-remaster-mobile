package com.kylix.update_profile.ui

import androidx.activity.result.contract.ActivityResultContracts
import com.kylix.change_profile.databinding.ActivityUpdateProfileBinding
import com.kylix.common.base.BaseActivity
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.draw
import com.kylix.common.widget.bind
import com.kylix.common.widget.successSnackbar
import com.kylix.update_profile.validator.UpdateProfileValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProfileActivity : BaseActivity<ActivityUpdateProfileBinding>() {

    override val viewModel by viewModel<UpdateProfileViewModel>()

    private val pickImageGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.setUri(uri)
    }

    override fun inflateViewBinding(): ActivityUpdateProfileBinding {
        return ActivityUpdateProfileBinding.inflate(layoutInflater)
    }

    override fun ActivityUpdateProfileBinding.bind() {
        changeProfileAppBar.bind(
            scope = this@UpdateProfileActivity,
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

    override fun constraintValidator(): ConstraintValidator<ActivityUpdateProfileBinding> {
        return UpdateProfileValidator(this)
    }

    override fun observeState() {
        super.observeState()
        binding.apply {
            viewModel.user.observe {
                if (it == null) return@observe
                ivProfile.draw(this@UpdateProfileActivity, it.avatar) {
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
                ivProfile.draw(this@UpdateProfileActivity, it) {
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