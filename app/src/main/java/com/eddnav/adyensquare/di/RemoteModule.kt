package com.eddnav.adyensquare.di

import com.eddnav.adyensquare.data.remote.FoursquareService
import com.eddnav.adyensquare.di.scope.Application
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @author Eduardo Naveda
 */
@Module
class RemoteModule {

    @Application
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun retrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttp)
                .baseUrl(FoursquareService.BASE_URL)
                .build()
    }

}