package com.arcanium.data

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {
    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl
}
