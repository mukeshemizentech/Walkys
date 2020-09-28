package com.walky.walkys.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.walky.responses.HomeResponse
import com.walky.walkys.R
import com.walky.walkys.databinding.AdnentureItemBinding
import java.util.*


class AdventureAdapter(context: Context, arrayList: ArrayList<HomeResponse>) :
    RecyclerView.Adapter<AdventureAdapter.ViewHolder>() {
    private val arrayList: ArrayList<HomeResponse> = arrayList
    private val context: Context = context
    lateinit var mMap : GoogleMap

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdnentureItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.adnenture_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.binding.map.onCreate(null)
//        viewHolder.binding.map.getMapAsync(OnMapReadyCallback { googleMap ->
//            mMap = googleMap
////            mMap.uiSettings.isZoomControlsEnabled = false
//            val lat1 = "1.344376".toDouble()
//            val lng1 = "103.862162".toDouble()
//            val bord = LatLng(lat1, lng1)
////            mMap.addMarker(MarkerOptions().position(bord).title("Marker on board"))
////            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bord, 13.0f))
//        })
//        viewHolder.binding.map.postInvalidate()

    }
    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(binding: AdnentureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: AdnentureItemBinding = binding

    }

}
