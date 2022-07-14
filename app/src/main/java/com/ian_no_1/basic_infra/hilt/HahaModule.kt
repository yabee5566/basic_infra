package com.ian_no_1.basic_infra.hilt

import com.ian_no_1.basic_infra.util.HahaManager
import com.ian_no_1.basic_infra.util.HahaManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HahaModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindHahaManager(impl: HahaManagerImpl): HahaManager
}