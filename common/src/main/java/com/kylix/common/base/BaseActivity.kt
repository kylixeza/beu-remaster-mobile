package com.kylix.common.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.ScreenOrientation

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private lateinit var _binding: VB
    val binding get() = _binding

    abstract fun inflateViewBinding(): VB
    abstract fun VB.bind()

    open fun constraintValidator(): ConstraintValidator<VB>? { return null }
    open fun determineScreenOrientation(): ScreenOrientation? { return ScreenOrientation.PORTRAIT }
    open fun onBackPressedBehaviour() { finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateViewBinding()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val screenOrientation = determineScreenOrientation()

        requestedOrientation = if(screenOrientation != null) {
            if (screenOrientation == ScreenOrientation.PORTRAIT)
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }

        binding.apply {
            bind()
            constraintValidator()?.apply { validate() }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedBehaviour()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    
}