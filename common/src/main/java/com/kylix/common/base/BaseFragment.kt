package com.kylix.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kylix.common.R
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.ScreenOrientation
import com.kylix.common.widget.buildLoadingDialog
import com.kylix.common.widget.errorSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding

    lateinit var fragmentView: View
    lateinit var parent: AppCompatActivity

    private val loadingDialog by lazy {
        requireContext().buildLoadingDialog()
    }

    open val viewModel: BaseViewModel? = null

    abstract fun inflateViewBinding(container: ViewGroup?): VB
    abstract fun VB.bind()

    open fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) { }
    open fun onViewCreatedBehaviour() { }
    open fun constraintValidator(): ConstraintValidator<VB>? { return null }
    open fun determineScreenOrientation(): ScreenOrientation { return ScreenOrientation.PORTRAIT }
    open fun onDestroyBehaviour() { }
    open fun onBackPressedBehaviour() { }
    open fun onDataSuccessLoaded() {}
    open fun systemBarColor(): Int { return R.color.white }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewBehaviour(inflater, container)
        if(_binding == null) {
            _binding = inflateViewBinding(container)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedBehaviour()
        
        this.fragmentView = view
        this.parent = activity as AppCompatActivity

        if(determineScreenOrientation() == ScreenOrientation.PORTRAIT) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), systemBarColor())

        binding?.apply {
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
                if (uiState.isError) binding?.root?.errorSnackbar(uiState.errorMessage)
                if (uiState.isSuccess) onDataSuccessLoaded()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onDestroyView() {
       super.onDestroyView()
        onDestroyBehaviour()
        _binding = null
    }
}