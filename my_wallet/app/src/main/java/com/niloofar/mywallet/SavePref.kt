package com.hossein.mywallet

import android.app.Application
import android.content.Context

class SavePref : Application() {
    override fun onCreate() {
        super.onCreate()
        var Context = applicationContext
        var context: Context? = null
    }

    fun SAVE(context: Context, intiger: Int) {

        val sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putInt("pass", intiger)
        }.apply()
    }


    fun GET(context: Context): Int {
        val SharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        val savedint:Int = SharedPreferences.getInt("pass",0)
        return SharedPreferences.getInt("pass",0)
    }


}