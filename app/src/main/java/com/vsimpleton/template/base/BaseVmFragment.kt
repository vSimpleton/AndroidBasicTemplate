package com.vsimpleton.template.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseVmFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {

    private lateinit var _viewModel: VM
    protected val mViewModel: VM get() = _viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = createViewModel()
    }

    abstract fun createViewModel(): VM

}