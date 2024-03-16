package com.kylix.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kylix.common.util.ConstraintValidator
import com.kylix.common.util.ScreenOrientation
import com.kylix.common.widget.buildLoadingDialog
import com.kylix.common.widget.errorSnackbar
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDialogFragment<VB: ViewBinding>: BottomSheetDialogFragment() {

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
    open fun onBackPressedBehaviour() { requireActivity().finish() }
    open fun onDataSuccessLoaded() {}

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

        binding?.apply {
            bind()
            constraintValidator()?.apply { validate() }

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedBehaviour()
            }
        }
        observeState()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    open fun observeState() {
        lifecycleScope.launch {
            viewModel?.uiState?.collect { uiState ->
                if (uiState == null) return@collect
                if (uiState.isLoading) loadingDialog.show() else loadingDialog.dismiss()
                if (uiState.isError) binding?.root?.errorSnackbar(uiState.errorMessage)
                if (uiState.isSuccess) onDataSuccessLoaded()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyBehaviour()
        _binding = null
    }

}