package com.lime.android.util

import android.content.Context
import android.widget.ImageView
import com.lime.android.R
import com.squareup.picasso.Picasso

internal class LimeUtils {

    companion object{

        fun setImageUsingPicasso(context: Context, view: ImageView, url: String){
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).noFade().into(view);
        }
    }
}
