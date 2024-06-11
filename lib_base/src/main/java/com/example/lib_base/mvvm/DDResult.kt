package com.example.lib_base.mvvm

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
sealed class DDResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : DDResult<T>()
    data class Error(val exception: Exception) : DDResult<Nothing>(){
        init {

        }
    }

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