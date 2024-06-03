package com.example.binding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.binding.databinding.ActivityDataBindingBinding
import com.hi.dhl.binding.ext.databind

/**
 * @Author ley
 * @Email yule@ysten.com
 * @Since 2024/5/31 16:00
 * @Desc
 */
class DataBindingActivity : ComponentActivity() {
    companion object {
        const val TAG = "DataBindingActivity"
    }

    private val data1 = Data("我是0")
    private val data2 = ObservableField<Data>()

    private val mainViewModel by viewModels<MainViewModel>()
    private val binding: ActivityDataBindingBinding by databind(R.layout.activity_data_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this//直接赋值
        binding.setVariable(BR.vm, mainViewModel)//使用ViewDataBinding.setVariable(int variableId, @Nullable Object value)进行赋值

        binding.data = data1
        binding.data2 = data2
        data2.set(Data("我是1"))

        binding.list = ObservableArrayList<String?>().apply {
            add("我是list")
        }
        binding.map = ObservableArrayMap<String?, String?>().apply {
            put("name", "我是map")
        }

        binding.layout12.viewStub?.inflate()
        binding.setOnClickListener {
            Log.d(TAG, "click13")
        }
//        binding.data = Data("我是11")
//        binding.layout11.title11.text = "我是11"
    }

    override fun onResume() {
        super.onResume()
//        data1.title = "我是11"
//        data2.get()?.title = "我是12"
//        data2.set(data1)
    }

    fun click1(v: View) {
        Log.d(TAG, "click1")
    }

    fun click2() {
        Log.d(TAG, "click2")
    }

    val click3 = View.OnClickListener {
        Log.d(TAG, "click3")
    }

    fun click4(v: View) {
        Log.d(TAG, "click4")
    }

    data class Data(var title: String, val name: String? = null)

    //利用MediatorLiveData实现一个组合的LiveData--CombinedLiveData
    open class CombinedLiveData<T>(vararg liveData: LiveData<*>, block: () -> T) :
        MediatorLiveData<T>() {
        init {
            value = block()
            liveData.forEach {
                addSource(it) {
                    val newValue = block()
                    if (value != newValue) {
                        value = newValue
                    }
                }
            }
        }
    }

    fun <R, T1, T2> combineLiveData(
        liveData1: LiveData<T1>,
        liveData2: LiveData<T2>,
        block: (T1?, T2?) -> R
    ) = CombinedLiveData(liveData1, liveData2) { block(liveData1.value, liveData2.value) }
}