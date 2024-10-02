package com.trex.laxmiemi

import android.app.Application
import com.google.android.gms.common.internal.service.Common
import com.trex.laxmiemi.utils.CommonConstants

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CommonConstants.initialize(applicationContext)

    }
}