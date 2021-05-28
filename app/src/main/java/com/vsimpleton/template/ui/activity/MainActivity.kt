package com.vsimpleton.template.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.vsimpleton.template.api.StateResult
import com.vsimpleton.template.base.BaseVMActivity
import com.vsimpleton.template.databinding.ActivityMainBinding
import com.vsimpleton.template.expand.setOnSingleListener
import com.vsimpleton.template.observer.MessageEvent
import com.vsimpleton.template.observer.MsgId
import com.vsimpleton.template.ui.fragment.MainFragment
import com.vsimpleton.template.data.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.ivImg.setOnSingleListener {

        }

        supportFragmentManager.beginTransaction().replace(mBinding.fgContainer.id, MainFragment()).commit()

        initViewModel()
    }

    private fun initViewModel() {
        mViewModel.getArticleList()
        mViewModel.articleLiveData.observe(this, Observer {
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

    override fun handleEvent(msg: MessageEvent) {
        when (msg.id) {
            MsgId.MSG_EXAMPLE -> {
                Toast.makeText(this, "收到了通知", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // TODO 优化：需要适用于所有创建ViewModel的方式
    override fun createViewModel(): MainViewModel {
        return mainViewModel
    }

}