package com.batzalcancia.auth.presentation.features.register

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.batzalcancia.auth.R
import com.batzalcancia.auth.databinding.FragmentRegisterBinding
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.core.helpers.containerTransition
import com.batzalcancia.core.helpers.setDropdown
import com.batzalcancia.core.helpers.textChangesFlow
import com.batzalcancia.core.utils.CountryUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewBinding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = containerTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentRegisterBinding.bind(view)

        viewBinding.edtCountry.setDropdown(requireContext(), CountryUtils.countries.map { it.name })

        viewBinding.edtUsername.textChangesFlow()
            .debounce(300)
            .onEach { viewModel.username = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.edtPassword.textChangesFlow()
            .debounce(300)
            .onEach { viewModel.password = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.edtCountry.textChangesFlow()
            .debounce(300)
            .onEach { viewModel.country = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnRegister.setOnClickListener {
            viewModel.onRegisterClicked()
        }

        setupOutputs()
    }


    private fun setupOutputs() {
        viewModel.isButtonEnabled.onEach {
            viewBinding.btnRegister.isEnabled = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.registerState.onEach {
            viewBinding.prgRegister.isVisible = it == UiState.Loading
            viewBinding.btnRegister.isInvisible = it == UiState.Loading

            viewBinding.edtPassword.isEnabled = it != UiState.Loading
            viewBinding.edtUsername.isEnabled = it != UiState.Loading

            if (it == UiState.Complete) {
                findNavController().navigate(R.id.action_global_to_users)
            } else if (it is UiState.Error) {
                if (it.throwable is SQLiteConstraintException) {
                    viewBinding.tilUsername.error =
                        it.throwable.localizedMessage ?: getString(R.string.generic_error_message)
                } else {
                    viewBinding.tilUsername.error = null
                    Snackbar.make(
                        viewBinding.root,
                        it.throwable.localizedMessage ?: getString(R.string.generic_error_message),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}