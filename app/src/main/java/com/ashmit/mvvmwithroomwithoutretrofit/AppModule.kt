package com.ashmit.mvvmwithroomwithoutretrofit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class AppModule :Application() {
}