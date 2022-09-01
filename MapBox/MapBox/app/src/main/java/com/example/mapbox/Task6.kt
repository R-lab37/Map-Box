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
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions

class Task6 : AppCompatActivity() , OnMapClickListener {

    private lateinit var mapboxMap: MapboxMap
    private lateinit var viewAnnotationManager: ViewAnnotationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task6)
        val binding = ActivityTask3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewAnnotationManager = binding.mapView.viewAnnotationManager

        mapboxMap = binding.mapView.getMapboxMap().apply {
            loadStyleUri(Style.MAPBOX_STREETS) {
                addOnMapClickListener(this@Task6)
                binding.fabStyleToggle.setOnClickListener {
                    when (getStyle()?.styleURI) {
                        Style.MAPBOX_STREETS -> loadStyleUri(Style.SATELLITE_STREETS)
                        Style.SATELLITE_STREETS -> loadStyleUri(Style.MAPBOX_STREETS)
                    }
                }
                Toast.makeText(this@Task6, STARTUP_TEXT, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapClick(point: Point): Boolean {
        addViewAnnotation(point)
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun addViewAnnotation(point: Point) {
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            resId = R.layout.item_callout_view,
            options = viewAnnotationOptions {
                geometry(point)
                allowOverlap(true)
            }
        )
        ItemCalloutViewBinding.bind(viewAnnotation).apply {
            textNativeView.text = "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
            closeNativeView.setOnClickListener {
                viewAnnotationManager.removeViewAnnotation(viewAnnotation)
            }
            selectButton.setOnClickListener { b ->
                val button = b as Button
                val isSelected = button.text.toString().equals("SELECT", true)
                val pxDelta = if (isSelected) SELECTED_ADD_COEF_PX else -SELECTED_ADD_COEF_PX
                button.text = if (isSelected) "DESELECT" else "SELECT"
                viewAnnotationManager.updateViewAnnotation(
                    viewAnnotation,
                    viewAnnotationOptions {
                        selected(isSelected)
                    }
                )
                (button.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    bottomMargin += pxDelta
                    rightMargin += pxDelta
                    leftMargin += pxDelta
                }
                button.requestLayout()
            }
        }
    }

    private companion object {
        const val SELECTED_ADD_COEF_PX = 25
        const val STARTUP_TEXT = "Click on a map to add a view annotation."
    }
}