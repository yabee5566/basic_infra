package com.ian_no_1.basic_infra.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiRetrofit


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiLoggingInterceptor

