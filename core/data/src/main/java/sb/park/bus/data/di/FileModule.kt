package sb.park.bus.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileModule {

    private const val FILE_NAME = "bus.json"

    @Provides
    @Singleton
    fun provideFile(@ApplicationContext context: Context): File = File(context.filesDir, FILE_NAME)
}