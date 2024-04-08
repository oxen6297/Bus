package sb.park.bus.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sb.park.bus.data.repository.BitCoinRepository
import sb.park.bus.data.repository.BitCoinRepositoryImpl
import sb.park.bus.data.repository.BusIdRepository
import sb.park.bus.data.repository.BusIdRepositoryImpl
import sb.park.bus.data.repository.BusStationRepository
import sb.park.bus.data.repository.BusStationRepositoryImpl
import sb.park.bus.data.repository.FavoriteRepository
import sb.park.bus.data.repository.FavoriteRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepoModule {

    @Binds
    abstract fun provideBitCoinRepository(bitCoinRepositoryImpl: BitCoinRepositoryImpl): BitCoinRepository

    @Binds
    abstract fun provideBusStationRepository(busStationRepositoryImpl: BusStationRepositoryImpl): BusStationRepository

    @Binds
    abstract fun provideBusIdRepository(busIdRepositoryImpl: BusIdRepositoryImpl): BusIdRepository

    @Binds
    abstract fun provideFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}