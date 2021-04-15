package com.vsimpleton.template.expand

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vsimpleton.template.R

/**
 * 加载普通图片
 */
fun ImageView.loadImage(context: Context, path: String, placeholder: Int = R.drawable.shape_f5f5f5, useCache: Boolean = false) {
    val options = getOptions(placeholder, useCache)
    Glide.with(context).load(path).apply(options).into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage() {

}

private fun ImageView.getOptions(placeholder: Int = R.drawable.shape_f5f5f5, useCache: Boolean = false): RequestOptions {
    return RequestOptions().apply {
        placeholder(placeholder)
        priority(Priority.HIGH)
        if (useCache) {
            diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        }
    }
}