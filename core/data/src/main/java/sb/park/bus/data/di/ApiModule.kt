package sb.park.bus.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sb.park.bus.data.BitCoinService
import sb.park.bus.data.util.BIT_COIN
import sb.park.bus.data.util.BUS
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    @BitCoinRetrofit
    fun provideBitCoinRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BIT_COIN)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @BusLocRetrofit
    fun provideBusLocRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BUS)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(@BitCoinRetrofit retrofit: Retrofit): BitCoinService = retrofit.create(BitCoinService::class.java)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BitCoinRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BusLocRetrofit
}