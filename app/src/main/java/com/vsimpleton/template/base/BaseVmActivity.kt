package com.vsimpleton.template.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding


abstract class BaseVmActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    private lateinit var _viewModel: VM
    protected val mViewModel: VM get() = _viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = createViewModel()
    }

    abstract fun createViewModel(): VM

}