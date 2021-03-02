package com.example.testtask0.ui.location

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import com.example.testtask0.R
import com.example.testtask0.ui.base.BaseFragment
import com.example.testtask0.ui.extension.load
import com.example.testtask0.ui.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationDetailsFragment : BaseFragment(R.layout.fragment_location_details) {

    private val args by navArgs<LocationDetailsFragmentArgs>()

    private val locationDataLayout: LinearLayout
        get() = view(R.id.locationDataLayout)
    private val locationImage: ImageView
        get() = view(R.id.image)
    private val map: MapView
        get() = view(R.id.mapView)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState)
        with(args.location) {
            supplyLocationData(this)
            loadMap(this)
        }
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        map.onPause()
        super.onPause()
    }

    override fun onStop() {
        map.onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        map.onDestroy()
        super.onDestroyView()
    }

    private fun supplyLocationData(location: Location) {
        locationDataLayout.removeAllViews()
        location.let {
            listOf(
                R.string.label_id to it.id,
                R.string.label_name to it.name,
                R.string.label_country to it.country
            )
        }.map { keyValueView(it.first, it.second) }.forEach(locationDataLayout::addView)
        locationImage.load(location.imageUrl)
    }

    private fun keyValueView(@StringRes stringResId: Int, value: String): View {
        return layoutInflater.inflate(R.layout.view_key_value_pair, null).apply {
            findViewById<TextView>(R.id.key).setText(stringResId)
            findViewById<TextView>(R.id.value).text = value
        }
    }

    private fun loadMap(location: Location) {
        map.getMapAsync { googleMap ->
            val coordinates = LatLng(location.latitude, location.longitude)
            googleMap.addMarker(MarkerOptions().position(coordinates).title(location.name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 13f))
        }
    }
}