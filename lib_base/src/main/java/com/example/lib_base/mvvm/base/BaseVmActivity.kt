package com.example.lib_base.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.lib_base.mvvm.getVmClazz
import com.example.lib_base.mvvm.util.notNull

/**
 * 作者　: hegaojianO
 * 时间　: 2019/12/12
 * 描述　: ViewModelActivity基类，把ViewModel注入进来了
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        super.onCreate(savedInstanceState)
    }


    override fun setContentView() {
        initDataBind().notNull({
            setContentView(it)
        }, {
            setContentView(getLayoutResId())
        })
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }


    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind(): View? {
        return null
    }

}