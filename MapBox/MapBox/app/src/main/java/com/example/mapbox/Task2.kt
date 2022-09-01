package com.example.mapbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mapbox.databinding.ActivityTask2Binding
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

var mapView1: MapView? = null
private lateinit var binding: ActivityTask2Binding

class Task2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTask2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView1 = findViewById(R.id.mapView1)
//        mapView1?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
//        mapView1 = binding.mapView.getMapboxMap()
// Where STYLE_URL is a string   mapbox://styles/rishikarya/cl7h85tns002l14lih66tpgp5
      //  binding.mapView1?.loadStyleUri(R.string.STYLE_URL)
//        new Style.Builder().fromUrl("yourcustomurl")
        mapView1?.getMapboxMap()?.loadStyleUri("mapbox://styles/rishikarya/cl7h85tns002l14lih66tpgp5")
    }

    override fun onStart() {
        super.onStart()
        mapView1?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView1?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView1?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView1?.onDestroy()
    }
}