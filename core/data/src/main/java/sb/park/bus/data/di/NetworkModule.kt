package sb.park.bus.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sb.park.bus.data.service.BitCoinService
import sb.park.bus.data.service.BusArriveService
import sb.park.bus.data.service.BusIdService
import sb.park.bus.data.service.BusLocationService
import sb.park.bus.data.service.BusStationInfoService
import sb.park.bus.data.service.BusStationService
import sb.park.bus.data.util.BIT_COIN
import sb.park.bus.data.util.BUS
import sb.park.bus.data.util.BUS_ID
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor {
        Timber.e(it)
    }.setLevel(HttpLoggingInterceptor.Level.BASIC)

    private const val TIME_OUT = 30L

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
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
    @BusRetrofit
    fun provideBusRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BUS)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @BusIdRetrofit
    fun provideBusIdRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BUS_ID)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideBitCoinService(@BitCoinRetrofit retrofit: Retrofit): BitCoinService =
        retrofit.create(BitCoinService::class.java)

    @Singleton
    @Provides
    fun provideBusLocationService(@BusRetrofit retrofit: Retrofit): BusLocationService =
        retrofit.create(BusLocationService::class.java)

    @Singleton
    @Provides
    fun provideBusStationService(@BusRetrofit retrofit: Retrofit): BusStationService =
        retrofit.create(BusStationService::class.java)

    @Singleton
    @Provides
    fun provideBusStationInfoService(@BusRetrofit retrofit: Retrofit): BusStationInfoService =
        retrofit.create(BusStationInfoService::class.java)

    @Singleton
    @Provides
    fun provideBusArriveService(@BusRetrofit retrofit: Retrofit): BusArriveService =
        retrofit.create(BusArriveService::class.java)

    @Singleton
    @Provides
    fun provideBusIdService(@BusIdRetrofit retrofit: Retrofit): BusIdService =
        retrofit.create(BusIdService::class.java)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BitCoinRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BusRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BusIdRetrofit
}