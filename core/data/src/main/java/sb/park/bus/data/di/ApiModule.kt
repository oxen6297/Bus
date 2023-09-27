package sb.park.bus.data.di

import com.google.gson.Gson
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val okHttpClient = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl("https://api.bithumb.com/public/ticker/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BitApi = retrofit.create(BitApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(bitApi: BitApi): BitCoinRepository = BitCoinRepositoryImpl(bitApi)
}