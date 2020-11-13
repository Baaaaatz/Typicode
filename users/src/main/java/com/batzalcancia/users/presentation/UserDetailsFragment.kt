package com.batzalcancia.users.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.batzalcancia.core.helpers.containerTransition
import com.batzalcancia.core.helpers.setContainerTransition
import com.batzalcancia.users.R
import com.batzalcancia.users.data.entities.User
import com.batzalcancia.users.databinding.FragmentUserDetailsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private lateinit var viewBinding: FragmentUserDetailsBinding

    private val userDetailsNavArgs by navArgs<UserDetailsFragmentArgs>()

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = containerTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentUserDetailsBinding.bind(view)

        val userDetails = Json.decodeFromString<User>(userDetailsNavArgs.userDetails)

        viewBinding.tlbMain.setupWithNavController(findNavController())

        viewBinding.root.setContainerTransition(
            getString(
                R.string.container_transition_name,
                userDetails.id.toString()
            )
        )

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.apbMain) { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.cstUserDetails) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom + viewBinding.cstUserDetails.paddingBottom)
            insets
        }

        userDetails.run {
            val address =
                "${this.address.suite}, ${this.address.street}, ${this.address.city}, ${this.address.zipcode}"

            viewBinding.txtName.text = name
            viewBinding.txtAddress.text = address
            viewBinding.txtEmail.text = email
            viewBinding.txtPhone.text = phone
            viewBinding.txtCompany.text = company.name
            viewBinding.txtWebsite.text = website
        }

        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).run {
            getMapAsync { map ->
                googleMap = map
                map.run {
                    uiSettings.isScrollGesturesEnabled = false
                    uiSettings.isZoomControlsEnabled = false
                    uiSettings.isRotateGesturesEnabled = false
                    val userLocation =
                        LatLng(userDetails.address.geo.lat, userDetails.address.geo.lng)
                    moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 2f))
                    addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .title(userDetails.address.street)
                    )
                }
            }
        }


    }
}