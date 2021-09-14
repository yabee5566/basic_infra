package com.ian_no_1.basic_infra.network.interceptor

import com.ian_no_1.basic_infra.network.ApiLoggingInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class InterceptorModule {
    @Binds
    @Singleton
    @ApiLoggingInterceptor
    abstract fun bindLoggingInterceptor(interceptor: LoggingInterceptor): Interceptor
}