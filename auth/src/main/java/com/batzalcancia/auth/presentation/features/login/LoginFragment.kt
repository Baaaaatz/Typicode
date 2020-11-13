package com.batzalcancia.auth.presentation.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.batzalcancia.auth.R
import com.batzalcancia.auth.databinding.FragmentLoginBinding
import com.batzalcancia.core.BaseFragment
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.core.helpers.prepareReturnTransition
import com.batzalcancia.core.helpers.textChangesFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var viewBinding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLoginBinding.bind(view)

        viewBinding.crdLogin.prepareReturnTransition(this)
        viewBinding.btnRegister.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginToRegister(),
                FragmentNavigatorExtras(viewBinding.crdLogin to viewBinding.crdLogin.transitionName)
            )
        }

        viewBinding.edtUsername.textChangesFlow()
            .debounce(300)
            .onEach { viewModel.username = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.edtPassword.textChangesFlow()
            .debounce(300)
            .onEach { viewModel.password = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnLogin.setOnClickListener {
            viewModel.onLoginClicked()
        }

        setupOutputs()
    }

    private fun setupOutputs() {
        viewModel.isButtonEnabled.onEach {
            viewBinding.btnLogin.isEnabled = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loginState.onEach {
            viewBinding.prgLogin.isVisible = it == UiState.Loading
            viewBinding.btnLogin.isInvisible = it == UiState.Loading

            viewBinding.edtPassword.isEnabled = it != UiState.Loading
            viewBinding.edtUsername.isEnabled = it != UiState.Loading

            if(it == UiState.Complete) {
                findNavController().navigate(R.id.action_global_to_users)
            }else if (it is UiState.Error) {
                viewBinding.btnLogin.isEnabled = false
                Snackbar.make(viewBinding.root, it.throwable.localizedMessage ?: "Error!", Snackbar.LENGTH_LONG).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}