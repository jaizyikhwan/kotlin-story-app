    package com.dicoding.picodiploma.loginwithanimation.di

    import android.content.Context
    import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
    import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
    import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiConfig
    import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
    import com.dicoding.picodiploma.loginwithanimation.data.repository.StoryRepository
    import dagger.Module
    import dagger.Provides
    import dagger.hilt.InstallIn
    import dagger.hilt.android.qualifiers.ApplicationContext
    import dagger.hilt.components.SingletonComponent
    import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Singleton
        @Provides
        fun provideUserPreference(@ApplicationContext context: Context): UserPreference {
            return UserPreference.getInstance(context.dataStore)
        }

        @Singleton
        @Provides
        fun provideApiService(@ApplicationContext context: Context): ApiService {
            return ApiConfig.getApiService(context)
        }


        @Singleton
        @Provides
        fun provideStoryRepository(apiService: ApiService): StoryRepository {
            return StoryRepository(apiService)
        }
    }