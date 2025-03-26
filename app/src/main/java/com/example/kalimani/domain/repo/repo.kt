package com.example.kalimani.domain.repo

import android.net.Uri
import com.example.kalimani.common.Resource
import com.example.kalimani.domain.model.SignupRequest
import com.example.kalimani.domain.model.UpdateUserProfile
import com.example.kalimani.domain.model.UserProfile

interface Repo {
    suspend fun signUp(signupRequest: SignupRequest): Resource<String>
    suspend fun login(email: String, password: String): Resource<String>
    suspend fun sendEmailVerification(): Resource<String>
    suspend fun logOut()
    suspend fun getUserProfile(userId: String): Resource<UserProfile>
    suspend fun updateUserProfile(userId: String, profile: UpdateUserProfile): Resource<Unit>
    //suspend fun uploadProfilePicture(userId: String, imageUri: Uri): Resource<String>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
}