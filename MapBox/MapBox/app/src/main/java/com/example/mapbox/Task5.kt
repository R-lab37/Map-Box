package com.example.mapbox

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.mapbox.databinding.ActivityTask3Binding
import com.example.mapbox.databinding.ActivityTask5Binding
import com.example.mapbox.databinding.ItemCalloutViewBinding
import com.example.mapbox.databinding.TaskFifthlayoutBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions

class Task5 : AppCompatActivity(), OnMapClickListener {

    private lateinit var mapboxMap: MapboxMap
    private lateinit var viewAnnotationManager: ViewAnnotationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task5)
        val binding = ActivityTask5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewAnnotationManager = binding.mapView.viewAnnotationManager
        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    addAnnotationToMap()
                }
            }
        )
        mapboxMap = binding.mapView.getMapboxMap().apply {
            loadStyleUri(Style.MAPBOX_STREETS) {
                addOnMapClickListener(this@Task5)
                binding.fabStyleToggle.setOnClickListener {
                    when (getStyle()?.styleURI) {
                        Style.MAPBOX_STREETS -> loadStyleUri(Style.SATELLITE_STREETS)
                        Style.SATELLITE_STREETS -> loadStyleUri(Style.MAPBOX_STREETS)
                    }
                }
                Toast.makeText(this@Task5, STARTUP_TEXT, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addAnnotationToMap() {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@Task5,
            R.drawable.img
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                .withPoint(Point.fromLngLat(18.06, 59.31))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)
// Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    override fun onMapClick(point: Point): Boolean {
        addViewAnnotation(point)
        return true
    }


    @SuppressLint("SetTextI18n")
    private fun addViewAnnotation(point: Point) {
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            resId = R.layout.task_fifthlayout,
            options = viewAnnotationOptions {
                geometry(point)
                allowOverlap(true)
            }
        )
        TaskFifthlayoutBinding.bind(viewAnnotation).apply {
            textNativeView.text = "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
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