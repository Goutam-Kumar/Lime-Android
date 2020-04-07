package com.lime.android.screens.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lime.android.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
    }

    fun setTitle(title: String){
        findViewById<TextView>(R.id.txt_title).text = title
    }

    fun setMenuVisibility(visibility: Int){
        findViewById<ImageView>(R.id.img_menu).visibility = visibility
    }

    fun setBackButtonVisibility(visibility: Int){
        findViewById<ImageView>(R.id.img_back).visibility = visibility
    }

    fun setToolbarVisibiliy(visibility: Int){
        findViewById<Toolbar>(R.id.toolbar).visibility = visibility
    }
}
