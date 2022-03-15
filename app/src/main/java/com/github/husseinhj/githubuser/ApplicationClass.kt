package com.github.husseinhj.githubuser

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext
import com.github.husseinhj.githubuser.modules.*

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApplicationClass)
            modules(listOf(
                applicationModule,
                repositoryModules,
                viewModelModules,
                webServiceModules,
                retrofitModule
            ))
        }
    }
}