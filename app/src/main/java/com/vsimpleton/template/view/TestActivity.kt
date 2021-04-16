package com.vsimpleton.template.view

import android.os.Bundle
import com.vsimpleton.template.base.BaseActivity
import com.vsimpleton.template.databinding.ActivityTestBinding
import com.vsimpleton.template.observer.ConcreteObservable
import com.vsimpleton.template.observer.MessageEvent
import com.vsimpleton.template.observer.MsgId

class TestActivity : BaseActivity<ActivityTestBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConcreteObservable.notifyObservers(MessageEvent(MsgId.MSG_EXAMPLE))
    }
}