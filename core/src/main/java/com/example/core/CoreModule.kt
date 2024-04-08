package com.example.core

import android.app.Application

object CoreModule {
    var baseUrl: String? = null
    var application: Application? = null

    fun init(application: Application, baseUrl: String? = null) {
        this.application = application
        this.baseUrl = baseUrl
    }
}