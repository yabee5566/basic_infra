package com.ian_no_1.basic_infra.network

import android.content.Context
import com.ian_no_1.basic_infra.R
import com.ian_no_1.basic_infra.const.ApiConst
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    //TODO: separate these fun to different module if hospital is good

    @Provides
    @ApiRetrofit
    @Singleton
    fun provideApiRetrofit(
        @ApplicationContext context: Context,
        moshi: Moshi,
        @ApiClient okHttpClient: OkHttpClient,
    ):Retrofit{
        val apiHost = context.getString(R.string.default_api_host)
        return Retrofit.Builder()
            .baseUrl(apiHost)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    @ApiClient
    fun provideApiClient(
        @ApiLoggingInterceptor loggingInterceptor:Interceptor
    ) :OkHttpClient{
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = ApiConst.MAX_PARALLEL_REQUESTS
        dispatcher.maxRequestsPerHost = ApiConst.MAX_PARALLEL_REQUESTS_PER_HOST

        return OkHttpClient.Builder()
            .readTimeout(ApiConst.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(ApiConst.CONN_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectionPool(
                ConnectionPool(ApiConst.IDLE_CONNECTION_NUMBER, ApiConst.KEEP_ALIVE_TIMEOUT, TimeUnit.SECONDS)
            )
            .retryOnConnectionFailure(false)
            .dispatcher(dispatcher)
            .addInterceptor(loggingInterceptor)
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()
    }


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideDuckApi(@ApiRetrofit retrofit: Retrofit): DuckApi {
        return retrofit.create(DuckApi::class.java)
    }
}