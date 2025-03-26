package com.example.kalimani

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GoogleSignInUtils {

    companion object {

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading

        fun doGoogleSignIn(
            context: Context,
            scope: CoroutineScope,
            launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
            login: () -> Unit
        ) {
            val credentialManager = CredentialManager.create(context)

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getCredentialOptions(context))
                .build()

            val firestore = FirebaseFirestore.getInstance()

            scope.launch {
                _isLoading.value = true  // Start loading
                try {
                    val result = credentialManager.getCredential(context,request)
                    when(result.credential){
                        is CustomCredential ->{
                            if(result.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                                val googleTokenId = googleIdTokenCredential.idToken
                                val authCredential = GoogleAuthProvider.getCredential(googleTokenId,null)
                                val user = Firebase.auth.signInWithCredential(authCredential).await().user
                                user?.let {
                                    if (!it.isAnonymous) {
                                        // Extract user details
                                        val userData = hashMapOf(
                                            "firstName" to it.displayName?.split(" ")?.firstOrNull(),
                                            "lastName" to it.displayName?.split(" ")?.getOrNull(1),
                                            "email" to it.email,
                                            "uid" to it.uid // Use UID as a unique identifier
                                        )

                                        // Save data to Firestore
                                        firestore.collection("users").document(it.uid)
                                            .set(userData)
                                            .addOnSuccessListener {
                                                // Data saved successfully
                                                login.invoke()
                                                _isLoading.value = false  // Stop loading
                                            }
                                            .addOnFailureListener { exception ->
                                                // Handle Firestore save failure
                                                exception.printStackTrace()
                                                _isLoading.value = false  // Stop loading
                                            }
                                    } else {
                                        _isLoading.value = false  // Stop loading if user is anonymous
                                    }
                                } ?: run {
                                    _isLoading.value = false  // Stop loading if user is null
                                }
                            }
                        }
                        else -> _isLoading.value = false
                    }
                }catch (e:NoCredentialException){
                    launcher?.launch(getIntent())
                    _isLoading.value = false
                }catch (e:GetCredentialException){
                    e.printStackTrace()
                    _isLoading.value = false
                }
            }
        }

        private fun getIntent():Intent{
            return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
            }
        }

        private fun getCredentialOptions(context: Context):CredentialOption{
            return GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(context.getString(R.string.web_Clint_id))
                .build()
        }
    }
}