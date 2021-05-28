package com.vsimpleton.template.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.vsimpleton.template.observer.ConcreteObservable
import com.vsimpleton.template.observer.MessageEvent
import com.vsimpleton.template.observer.Observer
import java.lang.reflect.ParameterizedType

open class BaseActivity<VB : ViewBinding> : AppCompatActivity(), Observer {

    protected lateinit var mBinding: VB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 通过反射创建ViewBinding
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(null, layoutInflater) as VB

        setContentView(mBinding.root)

        init()
    }

    private fun init() {
        ConcreteObservable.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ConcreteObservable.unRegister(this)
    }

    override fun update(msg: MessageEvent) {
        handleEvent(msg)
    }

    open fun handleEvent(msg: MessageEvent) {

    }

}