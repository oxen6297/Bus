package sb.park.bus.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sb.park.bus.data.BitApi
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.bus.data.repository.BitCoinRepositoryImpl
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
        .baseUrl("https://api.bithumb.com/public/ticker/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @BusLocRetrofit
    fun provideBusLocRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtidList")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(@BitCoinRetrofit retrofit: Retrofit): BitApi = retrofit.create(BitApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(bitApi: BitApi): BitCoinRepository = BitCoinRepositoryImpl(bitApi)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BitCoinRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    private annotation class BusLocRetrofit
}