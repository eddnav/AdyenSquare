package com.eddnav.adyensquare.di.module

import com.eddnav.adyensquare.di.scope.Application
import com.eddnav.adyensquare.remote.FoursquareService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Eduardo Naveda
 */
@Module
class RemoteModule {

    @Application
    @Provides
    fun okHttpClient(): OkHttpClient {
        val userlessFoursquareInterceptor = Interceptor({
            val original = it.request()
            val originalUrl = original.url()

            if (originalUrl.host() == FoursquareService.HOST) {
                val url = original.url().newBuilder()
                        .addQueryParameter(FoursquareService.CLIENT_ID_PARAM, FoursquareService.CLIENT_ID)
                        .addQueryParameter(FoursquareService.CLIENT_SECRET_PARAM, FoursquareService.CLIENT_SECRET)
                        .addQueryParameter(FoursquareService.VERSIONING_PARAM, FoursquareService.VERSIONING)
                        .build()
                it.proceed(original.newBuilder().url(url).build())
            } else {
                it.proceed(original)
            }
        })
        return OkHttpClient().newBuilder()
                .addInterceptor(userlessFoursquareInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Application
    @Provides
    fun retrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(okHttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(FoursquareService.BASE_URL)
                .build()
    }

    @Application
    @Provides
    fun foursquareService(retrofit: Retrofit): FoursquareService = retrofit.create(FoursquareService::class.java)
}