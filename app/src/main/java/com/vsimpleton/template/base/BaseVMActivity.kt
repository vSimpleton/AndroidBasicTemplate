package com.vsimpleton.template.base

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = createViewModel()

        mViewModel.errorLiveData.observe(this, Observer {
            onError(it.message)
        })

        mViewModel.loadingLiveData.observe(this, Observer {
            onLoading(it)
        })
    }

    /**
     * 网络返回错误时，可直接使用默认的Toast方式，也可重写该方法
     */
    open fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 未加载完成时，可直接使用默认的Loading，也可根据不同的需求重写该方法
     */
    open fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            Toast.makeText(this, "开始加载", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "结束加载", Toast.LENGTH_SHORT).show()
        }
    }

    abstract fun createViewModel(): VM

}