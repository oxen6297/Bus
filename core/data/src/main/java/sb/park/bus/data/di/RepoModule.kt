package sb.park.bus.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.bus.data.repository.BitCoinRepositoryImpl
import sb.park.bus.data.repository.BusSearchRepository
import sb.park.bus.data.repository.BusSearchRepositoryImpl
import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.repository.BusStationRepositoryImpl
import sb.park.bus.data.repository.DataStoreRepository
import sb.park.bus.data.repository.DataStoreRepositoryImpl
import sb.park.bus.data.repository.FavoriteRepository
import sb.park.bus.data.repository.FavoriteRepositoryImpl
import sb.park.bus.data.repository.FileRepository
import sb.park.bus.data.repository.FileRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepoModule {

    @Binds
    abstract fun provideBitCoinRepository(bitCoinRepositoryImpl: BitCoinRepositoryImpl): BitCoinRepository

    @Binds
    abstract fun provideBusStationRepository(busStationRepositoryImpl: BusStationRepositoryImpl): BusStationRepository

    @Binds
    abstract fun provideBusSearchRepository(busSearchRepositoryImpl: BusSearchRepositoryImpl): BusSearchRepository

    @Binds
    abstract fun provideFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    abstract fun provideChartPreferencesRepository(chartPreferencesRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    abstract fun provideFile(fileRepositoryImpl: FileRepositoryImpl): FileRepository
}