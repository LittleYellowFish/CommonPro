package com.example.binding

import android.content.Context

/**
 * @Author ley
 * @Email yule@ysten.com
 * @Since 2024/5/31 19:14
 * @Desc
 */
object AppUtils {

    @JvmStatic
    fun getAppInfo(context: Context?) =
        context?.let {
            "packageName: ${it.packageName}, \nversionName: ${
                it.packageManager.getPackageInfo(
                    it.packageName,
                    0
                ).versionName
            }"
        }
}