package com.batzalcancia.core

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(
    layout: Int,
    private val setupBinding: (View) -> VB,
    private val hasToolbar: Boolean = false
) : Fragment(layout) {
    abstract val viewModel: VM
    lateinit var viewBinding: VB
    lateinit var tlbMain: MaterialToolbar

    abstract fun setupInputs()
    abstract fun setupOutputs()
    abstract fun setupViews()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = setupBinding(view)
        if (hasToolbar) {
//            tlbMain = viewBinding.root.findViewById(R.id.tlb_main)
            tlbMain.setupWithNavController(findNavController())
            ViewCompat.setOnApplyWindowInsetsListener(tlbMain) { v, insets ->
                v.updatePadding(top = insets.systemWindowInsetTop)
                insets
            }
        }
        (viewBinding.root as ViewGroup).apply {
            clipToPadding = false
            clipChildren = false
        }
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
        setupViews()
        setupInputs()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupOutputs()
    }
}
