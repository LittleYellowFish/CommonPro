package com.example.binding

import androidx.lifecycle.ViewModel

/**
 * @Author ley
 * @Email yule@ysten.com
 * @Since 2024/5/31 18:47
 * @Desc
 */
class MainViewModel : ViewModel() {

    fun getTitle(type: Int): String {

        return "我是$type"
    }
}