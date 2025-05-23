package com.example.movieschallenge.di

import com.example.movieschallenge.BuildConfig
import com.example.movieschallenge.core.coroutines.CoroutinesDispatchers
import com.example.movieschallenge.data.datasource.remote.retrofit.MoviesServiceRetrofit
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutinesDispatchers() = CoroutinesDispatchers()

    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain: Interceptor.Chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header(AUTHORIZATION_HEADER, BuildConfig.API_TOKEN)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(okHttpInterceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(okHttpInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) = okHttpClientBuilder.build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideMoviesServiceRetrofit(retrofit: Retrofit): MoviesServiceRetrofit = retrofit.create(MoviesServiceRetrofit::class.java)

    private const val AUTHORIZATION_HEADER = "Authorization"
}
