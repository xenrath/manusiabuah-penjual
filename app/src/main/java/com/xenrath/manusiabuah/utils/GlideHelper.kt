package com.xenrath.manusiabuah.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xenrath.manusiabuah.R
import com.xenrath.manusiabuah.data.Constant
import java.io.File

class GlideHelper {
    companion object {
        fun setImage(context: Context, urlImage: String, imageView: ImageView) {
            Glide.with(context)
                .load(Constant.IP_IMAGE + urlImage)
                .centerCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView)
        }

        fun setImageFile(context: Context, file: File, imageView: ImageView) {
            Glide.with(context)
                .load(file)
                .centerCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imageView)
        }
    }

}