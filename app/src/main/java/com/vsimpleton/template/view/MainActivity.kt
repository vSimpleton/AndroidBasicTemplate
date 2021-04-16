package com.vsimpleton.template.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.vsimpleton.template.base.BaseActivity
import com.vsimpleton.template.databinding.ActivityMainBinding
import com.vsimpleton.template.expand.setOnSingleListener
import com.vsimpleton.template.observer.MessageEvent
import com.vsimpleton.template.observer.MsgId

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.ivImg.setOnSingleListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    override fun handleEvent(msg: MessageEvent) {
        when (msg.id) {
            MsgId.MSG_EXAMPLE -> {
                Toast.makeText(this, "收到了通知", Toast.LENGTH_SHORT).show()
            }
        }
    }

}