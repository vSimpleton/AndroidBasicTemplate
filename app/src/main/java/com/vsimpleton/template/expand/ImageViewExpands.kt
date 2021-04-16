package com.vsimpleton.template.expand

import android.content.Context
import android.graphics.Point
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.vsimpleton.template.R

/**
 * 加载普通图片
 */
fun ImageView.loadImage(context: Context, path: String, placeholder: Int = R.drawable.shape_f5f5f5) {
    val options = getOptions(placeholder)
    Glide.with(context).load(path).apply(options).into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(context: Context, path: String, placeholder: Int = R.drawable.shape_f5f5f5) {
    Glide.with(context).load(path).apply(getOptions(placeholder).circleCrop()).into(this)
}

/**
 * 加载圆角矩形图片
 */
fun ImageView.loadRoundImage(context: Context, path: String, cornerRadius: Int = 5, placeholder: Int = R.drawable.shape_f5f5f5) {
    Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(RoundedCorners(cornerRadius))).apply(getOptions(placeholder)).into(this)
}

///**
// * 加载圆角矩形图片
// */
//fun ImageView.loadRoundImage(context: Context, path: String, cornerRadius: Int = 5,
//                             placeholder: Int = R.drawable.shape_f5f5f5) {
//    Glide.with(context).load(path).transform(CenterCrop(), RoundedCorners(cornerRadius)).apply(getOptions(placeholder)).into(
//        this)
//}

/**
 * 按照图片的宽高比加载
 * 使用本属性的ImageView在xml布局中的width及height属性必须为WRAP_CONTENT
 * widthProportion 为相对于屏幕宽度的比例取值范围为0.0-1.0，当widthProportion=1.0时，ImageView的宽度为屏幕宽度
 * heightProportion为相对于图片宽度的显示比例
 */
fun ImageView.loadImageByProportion(context: Context, widthProportion: Float, heightProportion: Float) {
    this.adjustViewBounds = true

    val screenWidth: Int
    val point = Point()

    context.display?.getRealSize(point)
    screenWidth = point.x

    val params = this.layoutParams
    params.width = (screenWidth * widthProportion).toInt()
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams = params

    maxWidth = (screenWidth * widthProportion).toInt()
    maxHeight = (screenWidth * widthProportion * heightProportion).toInt()
    scaleType = ImageView.ScaleType.CENTER_CROP
}

private fun ImageView.getOptions(placeholder: Int = R.drawable.shape_f5f5f5): RequestOptions {
    return RequestOptions().apply {
        placeholder(placeholder)
        priority(Priority.HIGH)
    }
}