package com.example.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

/**
 * @Author ley
 * @Email yule@ysten.com
 * @Since 2024/5/31 18:27
 * @Desc
 */
@BindingAdapter("customTitle")
fun TextView.setCustomTitle(title: String?) {
    text = "标题$title"
}
// 多个参数进行绑定，requireAll=true，代表两个参数都设置了才生效，默认是true.
// 如果requireAll为false, 你没有填写的属性值将为null. 所以需要做非空判断.
@BindingAdapter(value = ["customTitle", "customSize"], requireAll = true)
fun TextView.setTextContent(title: String, size: Int) {
    text = "标题: $title"
    textSize = size.toFloat()
}


//自定义类型转换: Int -> String
@BindingConversion
fun int2string(integer: Int) = integer.toString()