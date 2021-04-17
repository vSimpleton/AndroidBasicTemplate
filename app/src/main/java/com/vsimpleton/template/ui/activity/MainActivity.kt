package com.vsimpleton.template.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.vsimpleton.template.base.BaseActivity
import com.vsimpleton.template.databinding.ActivityMainBinding
import com.vsimpleton.template.expand.setOnSingleListener
import com.vsimpleton.template.observer.MessageEvent
import com.vsimpleton.template.observer.MsgId
import com.vsimpleton.template.ui.fragment.MainFragment
import com.vsimpleton.template.data.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.ivImg.setOnSingleListener {

        }

        supportFragmentManager.beginTransaction().replace(mBinding.fgContainer.id, MainFragment()).commit()

    }

    override fun handleEvent(msg: MessageEvent) {
        when (msg.id) {
            MsgId.MSG_EXAMPLE -> {
                Toast.makeText(this, "收到了通知", Toast.LENGTH_SHORT).show()
            }
        }
    }

}