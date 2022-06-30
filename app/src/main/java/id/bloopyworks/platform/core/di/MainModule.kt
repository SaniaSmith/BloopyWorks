package id.bloopyworks.platform.core.di

import id.bloopyworks.platform.BuildConfig
import id.bloopyworks.platform.core.data.bloopyRepository
import id.bloopyworks.platform.core.data.source.local_datastore.DataStorePref.token_key
import id.bloopyworks.platform.core.data.source.local_datastore.DataStoreRepository
import id.bloopyworks.platform.core.data.source.remote.RemoteDataSource
import id.bloopyworks.platform.core.data.source.remote.network.ApiService
import id.bloopyworks.platform.core.utlis.Constant
import id.bloopyworks.platform.ui.landing.login.LoginViewModel
import id.bloopyworks.platform.ui.mainactivity.MainActivityViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { bloopyRepository(get()) }

    //datastore
    single(named(Constant.TOKEN_KEY)) { androidContext().token_key }
    single { DataStoreRepository(get(named(Constant.TOKEN_KEY))) }
}


val networkModule = module {
    single {
        val loggingInterceptor =
            if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val builder = request
                    .newBuilder()
                val mutatedRequest = builder.build()
                val response = chain.proceed(mutatedRequest)
                response
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}