package com.arcanium.movienight.data

import com.arcanium.movienight.data.user.UserRepositoryImpl
import com.arcanium.movienight.domain.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl
}
