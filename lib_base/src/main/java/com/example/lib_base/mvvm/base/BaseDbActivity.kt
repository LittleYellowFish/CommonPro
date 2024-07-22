package com.example.lib_base.mvvm.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.lib_base.mvvm.util.inflateBindingWithGeneric
import com.example.lib_base.mvvm.util.notNull

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/12
 * 描述　: 包含ViewModel 和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseDbActivity<DB : ViewDataBinding> : BaseActivity() {


    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId).apply {
            lifecycleOwner = this@BaseDbActivity
        }
    }

    lateinit var mDatabind: DB

    override fun setContentView() {
        initDataBind().notNull({
            setContentView(it)
        }, {
            setContentView(getLayoutResId())
        })
    }

    override fun getLayoutResId(): Int {
        return 0
    }

    private fun initDataBind(): View {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }


}