package com.batzalcancia.users.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.core.helpers.prepareReturnTransition
import com.batzalcancia.core.helpers.px
import com.batzalcancia.users.R
import com.batzalcancia.users.databinding.FragmentUsersBinding
import com.batzalcancia.users.databinding.ItemUserBinding
import com.batzalcancia.users.presentation.adapters.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private lateinit var viewBinding: FragmentUsersBinding
    private val viewModel: UsersViewModel by viewModels()

    private val usersAdapter by lazy {
        UsersAdapter(ItemUserBinding::bind)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentUsersBinding.bind(view)

        viewBinding.rcvUsers.prepareReturnTransition(this)


        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.apbMain) { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            v.updatePadding(bottom = 0 + viewBinding.root.paddingBottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.rcvUsers) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom + 16.px(requireContext()))
            insets
        }

        viewBinding.rcvUsers.adapter = usersAdapter

        setupOutputs()
    }

    private fun setupOutputs() {
        viewModel.users.onEach {
            usersAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.usersState.onEach {
            viewBinding.prgUsers.isVisible = it == UiState.Loading
            viewBinding.rcvUsers.isInvisible = it == UiState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}