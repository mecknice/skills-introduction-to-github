package com.example.kalimani.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kalimani.data.repoImpl.RepoImpl
import com.example.kalimani.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase { // Add this method
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun provideRepo(
        firebaseAuth: FirebaseAuth ,
        firebaseFirestore: FirebaseFirestore,
        firebaseDb: FirebaseDatabase
    ): Repo {
        return RepoImpl(firebaseAuth , firebaseFirestore, firebaseDb)

    }

}