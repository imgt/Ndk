package com.lg.ndk.listener

interface NativeListener {
    fun showLog(level: Int, msg: String?)
}