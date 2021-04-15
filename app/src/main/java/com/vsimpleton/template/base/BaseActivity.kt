package com.vsimpleton.template.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.vsimpleton.template.eventbus.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.reflect.ParameterizedType

open class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var mBinding: VB
    lateinit var mContext: FragmentActivity

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        // 通过反射创建ViewBinding
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(null, layoutInflater) as VB

        setContentView(mBinding.root)

        init()
    }

    private fun init() {
        EventBus.getDefault().register(this)
    }

    //事件传递
    @Subscribe
    fun onEventMainThread(msg: MessageEvent) {
        handleEvent(msg)
    }

    open fun handleEvent(msg: MessageEvent) {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}