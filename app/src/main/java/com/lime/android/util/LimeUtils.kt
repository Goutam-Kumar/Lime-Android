package com.lime.android.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.provider.Settings.Secure
import android.util.Log
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

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        @SuppressLint("HardwareIds")
        fun getDeviceID(context: Context): String? {
            return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        }

        fun showServiceError(msgWrapper: String?, context: Context) {
            msgWrapper?.let {
                val msg = it
                AlertDialog.Builder(context)
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok) { _, _ ->  }
                    .create().run {
                        setCanceledOnTouchOutside(false)
                        show()
                    }
            }
        }
        fun getDistanceInKm(pickupLat: Double, pickupLng: Double, dropLat: Double, dropLng: Double): Float{
            val results = FloatArray(1)
            Location.distanceBetween(
                pickupLat,
                pickupLng,
                dropLat,
                dropLng,
                results)
            val retRes = if(results.isNotEmpty()) results[0]/1000 else 0.0f
            Log.d(GLOBAL_TAG, "Distance:".plus(retRes).plus(" Km"))
            return retRes
        }
    }
}
