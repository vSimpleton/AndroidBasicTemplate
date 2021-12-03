package com.vsimpleton.template.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.vsimpleton.template.api.StateResult
import com.vsimpleton.template.base.BaseVMActivity
import com.vsimpleton.template.databinding.ActivityMainBinding
import com.vsimpleton.template.expand.setOnSingleListener
import com.vsimpleton.template.ui.fragment.MainFragment
import com.vsimpleton.template.data.viewmodel.MainViewModel
import com.vsimpleton.template.utils.createNormalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(mBinding.fgContainer.id, MainFragment()).commit()
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel.getArticleList()
        mViewModel.articleLiveData.observe(this, {
            when(it) {
                is StateResult.Success -> {

                }
                is StateResult.Error -> {
                    onError(it.exceptionInfo.message)
                }
                is StateResult.Loading -> {
                    onLoading(it.isLoading)
                }
            }
        })
    }

    override fun createViewModel(): MainViewModel {
        return mainViewModel
    }

}