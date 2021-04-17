package com.vsimpleton.template.utils

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.vsimpleton.template.api.ApiFactory
import com.vsimpleton.template.base.BaseViewModel

inline fun <reified T> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

inline fun <reified T : BaseViewModel> createViewModel(owner: FragmentActivity): T {
    return ViewModelProvider(owner).get(T::class.java)
}

inline fun <reified T> createApiFactory(baseUrl: String): T {
    return ApiFactory.create(baseUrl, T::class.java)
}