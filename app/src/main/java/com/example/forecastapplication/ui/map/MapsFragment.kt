package com.example.forecastapplication.ui.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.forecastapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsFragment : DialogFragment(), GoogleMap.OnMapClickListener{
    private lateinit var viewModel: MapViewModel
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val mumbai = LatLng(19.090760, 72.867333)
        //googleMap.addMarker(MarkerOptions().position(mumbai).title("Marker in Mumbai"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai))
        googleMap.setOnMapClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapClick(p0: LatLng) {
        Log.i(TAG, "onMapClick: $p0")
        val city: String? = getCompleteAddressString(p0.latitude, p0.longitude)
        city?.let { confirmCity(it) }
    }

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String? {
        var strAdd: String? = null
        var city: String? = null
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            when {
                addresses == null -> {
                    Log.w(TAG, "My Current location address: " + "No Address returned!")
                }
                addresses.isNotEmpty() -> {
                    val returnedAddress: Address = addresses[0]
                    val strReturnedAddress = StringBuilder("")
                    city = addresses[0].locality
                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                    Log.w(TAG, "My Current location address: $strReturnedAddress")
                    Log.w(TAG, "City: $city")
                }
                else -> {
                    Log.w(TAG, "My Current location address: " + "Can not get Address!")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w(TAG, "My Current location address: " + "Can not get Address!")
        }
        return city
    }

    private fun confirmCity(city: String){
        val builder: AlertDialog.Builder  = AlertDialog.Builder(requireContext())
        builder.setTitle(city)
        builder.setMessage("Your selected city")
        builder.setPositiveButton("Select") { dialogInterface: DialogInterface, i: Int ->
            /*findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "key",
                true
            )*/
            findNavController().previousBackStackEntry?.savedStateHandle?.set("city", city)
            dismiss()
        }
        builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }

    companion object {
        private const val TAG = "MapsFragment"
    }
}