package com.example.mapbox


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun task1(view: View) {
        val intent = Intent(this@MainActivity,Task1::class.java)
        startActivity(intent)
    }
    fun task2(view: View) {
        val intent = Intent(this@MainActivity,Task2::class.java)
        startActivity(intent)

    }
    fun task3(view: View) {
        val intent = Intent(this@MainActivity,Task3::class.java)
        startActivity(intent)
    }
    fun task4(view: View) {
        val intent = Intent(this@MainActivity,Task4::class.java)
        startActivity(intent)

    }
    fun task5(view: View) {
        val intent = Intent(this@MainActivity,Task5::class.java)
        startActivity(intent)
    }

    fun task6(view: View) { val intent = Intent(this@MainActivity,Task6::class.java)
        startActivity(intent)}
}