package com.kylix.auth.ui

import androidx.fragment.app.commit
import com.kylix.auth.databinding.ActivityAuthBinding
import com.kylix.auth.ui.login.LoginFragment
import com.kylix.common.base.BaseActivity
import com.kylix.common.base.BaseViewModel

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override fun inflateViewBinding(): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun ActivityAuthBinding.bind() {
        supportFragmentManager.commit {
            add(binding.authContainer.id, LoginFragment.newInstance(), LoginFragment::class.java.simpleName)
        }
    }
}