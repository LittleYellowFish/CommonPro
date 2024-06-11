package com.example.lib_base.mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by luyao
 * on 2019/5/31 15:44
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initView()
        initData()
    }

    open fun setContentView(){
        setContentView(getLayoutResId())
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

}