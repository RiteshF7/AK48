package com.trex.laxmiemi

import android.app.Application
import com.trex.laxmiemi.utils.CommonConstants

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonConstants.initialize(applicationContext)
    }
}
