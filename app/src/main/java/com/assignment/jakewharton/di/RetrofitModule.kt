package com.assignment.jakewharton.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.assignment.jakewharton.config.*
import com.assignment.jakewharton.data.GitHubRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat(DATE_FORMAT)
            .create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Singleton
    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + CACHE_CONTROL_MAX_STALE
                    ).build()
                chain.proceed(request)
            }
        }.cache(
            Cache(context.cacheDir, CACHE_HTTP_DISK_SIZE)
        ).build()
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): GitHubRetrofit {
        return retrofit
            .build()
            .create(GitHubRetrofit::class.java)
    }
}