package com.lime.android.util

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lime.android.R
import com.squareup.picasso.Picasso

internal class LimeUtils {

    companion object{

        fun setImageUsingPicasso(context: Context, view: ImageView, url: String){
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).noFade().into(view);
        }

        fun blinkAnimation(view: View){
            val animation = AlphaAnimation(0.7f,0.0f)
            animation.duration = 500
            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE
            animation.repeatMode = Animation.REVERSE
            view.startAnimation(animation)
        }
        // To Create ViewModel instance
        inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
            }

        fun makeToast(context: Context, message: String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
}
