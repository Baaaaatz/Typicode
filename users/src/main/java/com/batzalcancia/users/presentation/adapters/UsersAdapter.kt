package com.batzalcancia.users.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.batzalcancia.core.helpers.setContainerTransition
import com.batzalcancia.core.utils.ViewBindingViewHolder
import com.batzalcancia.users.R
import com.batzalcancia.users.data.entities.User
import com.batzalcancia.users.databinding.ItemUserBinding
import com.batzalcancia.users.presentation.UsersFragmentDirections
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class UsersAdapter(
    private val setupItemUserBinding: (View) -> ItemUserBinding
) : ListAdapter<User, ViewBindingViewHolder<ItemUserBinding>>(
    object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem

    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<ItemUserBinding> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewBindingViewHolder(setupItemUserBinding(view))
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<ItemUserBinding>, position: Int) {
        val user = getItem(position)
        holder.viewBinding.run {
            user?.let {
                txtName.text = it.name
                txtEmail.text = it.email
                txtAddress.text = it.address.city

                root.setContainerTransition(
                    root.context.getString(
                        R.string.container_transition_name,
                        it.id.toString()
                    )
                )

                root.setOnClickListener { view ->
                    view.findNavController().navigate(
                        UsersFragmentDirections.actionUsersToUserDetails(
                            Json.encodeToString(it)
                        ), FragmentNavigatorExtras(
                            root to root.transitionName,
                        )
                    )
                }
            }
        }
    }

}
