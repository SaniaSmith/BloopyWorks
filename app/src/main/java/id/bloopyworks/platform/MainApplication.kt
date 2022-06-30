package id.bloopyworks.platform

import android.app.Application
import id.bloopyworks.platform.core.di.networkModule
import id.bloopyworks.platform.core.di.repositoryModule
import id.bloopyworks.platform.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}