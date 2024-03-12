package com.kylix.common.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kylix.common.R
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.ScreenOrientation
import com.kylix.common.util.orZero
import com.kylix.common.widget.buildLoadingDialog
import com.kylix.common.widget.errorSnackbar
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private lateinit var _binding: VB
    val binding get() = _binding

    private val loadingDialog by lazy {
        this.buildLoadingDialog()
    }

    open val viewModel: BaseViewModel? = null

    abstract fun inflateViewBinding(): VB
    abstract fun VB.bind()

    open fun constraintValidator(): ConstraintValidator<VB>? { return null }
    open fun determineScreenOrientation(): ScreenOrientation? { return ScreenOrientation.PORTRAIT }
    open fun onBackPressedBehaviour() { finish() }
    open fun onDataSuccessLoaded() {}
    open fun systemBarColor(): Int? { return null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (systemBarColor() != null)
            window.statusBarColor = ContextCompat.getColor(this, systemBarColor().orZero())
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
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR
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

        lifecycleScope.launch {
            viewModel?.uiState?.collect { uiState ->
                if (uiState == null) return@collect
                if (uiState.isLoading) loadingDialog.show() else loadingDialog.dismiss()
                if (uiState.isError) binding.root.errorSnackbar(uiState.errorMessage)
                if (uiState.isSuccess) onDataSuccessLoaded()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun <T> StateFlow<T>.observe(block: (T) -> Unit) {
        lifecycleScope.launch {
            collect { block(it) }
        }
    }
    
}