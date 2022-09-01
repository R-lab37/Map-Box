package com.example.mapbox

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.mapbox.databinding.ActivityTask3Binding
import com.example.mapbox.databinding.ItemCalloutViewBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions

class Task3 : AppCompatActivity()  {
    private lateinit var mapboxMap: MapboxMap
    private lateinit var viewAnnotationManager: ViewAnnotationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTask3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewAnnotationManager = binding.mapView.viewAnnotationManager

        mapboxMap = binding.mapView.getMapboxMap().apply {
            loadStyleUri(Style.DARK) {
                binding.fabStyleToggle.setOnClickListener {
                    when (getStyle()?.styleURI) {
                        Style.DARK -> loadStyleUri(Style.LIGHT)
                        Style.LIGHT -> loadStyleUri(Style.DARK)
                    }
                }
                Toast.makeText(this@Task3, "anotation example ", Toast.LENGTH_LONG).show()
            }
        }
    }



}