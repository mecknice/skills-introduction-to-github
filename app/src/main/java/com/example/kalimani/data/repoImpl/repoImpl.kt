package com.example.kalimani.data.repoImpl

import android.net.Uri
import com.example.kalimani.common.Resource
import com.example.kalimani.domain.model.SignupRequest
import com.example.kalimani.domain.model.UpdateUserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.example.kalimani.domain.model.UserProfile
import com.example.kalimani.domain.repo.Repo
import kotlinx.coroutines.tasks.await
import com.google.firebase.storage.FirebaseStorage



import javax.inject.Inject

class RepoImpl @Inject constructor(
   private var auth: FirebaseAuth,
   private var firestore: FirebaseFirestore,
   private var firebaseDb: FirebaseDatabase
): Repo {


    override suspend fun signUp(signupRequest: SignupRequest): Resource<String> {
        return try {
            // Extract email and password from the signupRequest
            val email = signupRequest.email
            val password = signupRequest.password

            val result = auth.createUserWithEmailAndPassword(email, password).await()

            // Save user details in Firestore
            val userId = result.user?.uid
            if (userId != null) {
                val userMap = hashMapOf(
                    "firstName" to signupRequest.firstName,
                    "lastName" to signupRequest.lastName,
                    "email" to email
                )
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .set(userMap)
                    .await()
            }

            Resource.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Sign Up Failed")
        }
    }

    override suspend fun login(email: String, password: String): Resource<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success("Login Successful")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Login Failed")
        }
    }

    override suspend fun sendEmailVerification(): Resource<String> {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Resource.Success("Verification Email Sent")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to Send Verification Email")
        }
    }

    override suspend fun logOut() {
        auth.signOut()
    }

    override suspend fun getUserProfile(userId: String): Resource<UserProfile> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            val profile = document.toObject(UserProfile::class.java)
            if (profile != null) {
                Resource.Success(profile)
            } else {
                Resource.Error("User profile not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch profile")
        }
    }

    override suspend fun updateUserProfile(userId: String, profile: UpdateUserProfile): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId).set(profile).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update profile")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


//    override suspend fun uploadProfilePicture(userId: String, imageUri: Uri): Resource<String> {
//        return try {
//
//            val storageRef = storage.reference.child("profile_pictures/$userId.jpg")
//            storageRef.putFile(imageUri).await()
//            val downloadUrl = storageRef.downloadUrl.await().toString()
//            Resource.Success(downloadUrl)
//        } catch (e: Exception) {
//            Resource.Error(e.localizedMessage ?: "Failed to upload profile picture")
//        }
//    }
}