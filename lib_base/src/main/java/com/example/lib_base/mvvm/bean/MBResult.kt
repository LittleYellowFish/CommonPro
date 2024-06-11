package com.example.lib_base.mvvm.bean

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
sealed class MBResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : MBResult<T>()

    data class Error(val exception: Exception) : MBResult<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Error

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Error -> exception
            else -> null
        }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}