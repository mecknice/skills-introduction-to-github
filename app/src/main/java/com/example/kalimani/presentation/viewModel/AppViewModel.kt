package com.example.kalimani.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kalimani.common.Resource
import com.example.kalimani.domain.model.OnboardingData
import com.example.kalimani.domain.model.SignupRequest
import com.example.kalimani.domain.model.UpdateUserProfile
import com.example.kalimani.domain.model.UserProfile
import com.example.kalimani.domain.repo.Repo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: Repo) : ViewModel() {

    private val firebaseDatabase = Firebase.database.reference
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val uiState: StateFlow<ForgotPasswordState> = _uiState

    // Signup State
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> get() = _signUpState

    // Login State
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    private val _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> = _profileState

    private val _updateState = MutableLiveData<UpdateProfileState>()
    val updateState: LiveData<UpdateProfileState> = _updateState

    private val _uploadState = MutableLiveData<ProfileState>()
    val uploadState: LiveData<ProfileState> = _uploadState

    private val _onboardingState = MutableLiveData<OnboardingState>()
    val onboardingState: LiveData<OnboardingState> = _onboardingState



    fun sendPasswordResetEmail(email: String) {
        _uiState.value = ForgotPasswordState.Loading
        viewModelScope.launch {
            val result = repository.sendPasswordResetEmail(email)
            _uiState.value = if (result.isSuccess) {
                ForgotPasswordState.Success("Password reset email sent to $email")
            } else {
                ForgotPasswordState.Failure(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }




    // Function to authenticate with Firebase using Google ID Token
    fun firebaseAuthWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleSignIn", "Firebase Authentication Successful")
                    onSuccess()
                } else {
                    Log.e("GoogleSignIn", "Firebase Authentication Failed", task.exception)
                    onError(task.exception ?: Exception("Unknown Error"))
                }
            }
    }

    // Function to save onboarding data
    fun saveOnboardingData(userId: String, onboardingData: OnboardingData) {
        _onboardingState.value = OnboardingState(isLoading = true)

        // Reference to the user's data in Firebase
        val userRef = FirebaseDatabase.getInstance().getReference("users/$userId")

        userRef.child("onboardingData").setValue(onboardingData)
            .addOnSuccessListener {
                _onboardingState.value = OnboardingState(success = onboardingData)
            }
            .addOnFailureListener { exception ->
                _onboardingState.value = OnboardingState(error = exception.message)
            }
    }




    // Signup Function
    fun signUp(signupRequest: SignupRequest) = viewModelScope.launch {
        _signUpState.postValue(SignUpState(isLoading = true))
        val result = repository.signUp(signupRequest)
        when (result) {
            is Resource.Success -> _signUpState.postValue(SignUpState(success = (result.data)))
            is Resource.Error -> _signUpState.postValue(SignUpState(error = result.message))

            else -> {}
        }
    }

    // Login Function
    fun login(email: String, password: String) = viewModelScope.launch {
        _loginState.postValue(LoginState(isLoading = true))
        val result = repository.login(email, password)
        when (result) {
            is Resource.Success -> _loginState.postValue(LoginState(success = result.data))
            is Resource.Error -> _loginState.postValue(LoginState(error = result.message))
            else -> {}
        }
    }

    // Fetch User Profile
    fun fetchUserProfile(userId: String) = viewModelScope.launch {
        _profileState.postValue(ProfileState(isLoading = true))
        when (val result = repository.getUserProfile(userId)) {
            is Resource.Success -> _profileState.postValue(ProfileState(success = result.data))
            is Resource.Error -> _profileState.postValue(ProfileState(error = result.message))
            else -> {}
        }
    }

    // Update User Profile
    fun updateUserProfile(userId: String, profile: UpdateUserProfile) = viewModelScope.launch {
        _updateState.postValue(UpdateProfileState(isLoading = true))
        when (val result = repository.updateUserProfile(userId, profile)) {
            is Resource.Success -> _updateState.postValue(UpdateProfileState(success = profile))
            is Resource.Error -> _updateState.postValue(UpdateProfileState(error = result.message))
            else -> {}
        }
    }

    // Upload Profile Picture
//    fun uploadProfilePicture(userId: String, imageUri: Uri) = viewModelScope.launch {
//        _uploadState.postValue(ProfileState(isLoading = true))
//        when (val result = repository.uploadProfilePicture(userId, imageUri)) {
//            is Resource.Success -> {
//                _uploadState.postValue(ProfileState(success = UserProfile(profilePictureUrl = result.data)))
//            }
//            is Resource.Error -> _uploadState.postValue(ProfileState(error = result.message))
//            else -> {}
//        }
//    }


    // Logout
    fun logOut() = viewModelScope.launch {
        firebaseAuth.signOut()
        repository.logOut()
        _loginState.postValue(LoginState(success = "Logged Out Successfully"))
    }

    sealed class ForgotPasswordState {
        object Idle : ForgotPasswordState()
        object Loading : ForgotPasswordState()
        data class Success(val message: String) : ForgotPasswordState()
        data class Failure(val error: String) : ForgotPasswordState()
    }
}

data class SignUpState(
    val isLoading: Boolean = false,
    val success: String? = null,
    val error: String? = null
)

data class LoginState(
    val isLoading: Boolean = false,
    var success: String? = null, // Login Success Message
    val error: String? = null
)
data class EmailVerificationState(
    val isLoading: Boolean = false,
    val success: String? = null, // Verification Success Message
    val error: String? = null
)

data class ProfileState(
    val isLoading: Boolean = false,
    val success: UserProfile? = null,
    val error: String? = null
)


data class UpdateProfileState(
    val isLoading: Boolean = false,
    val success: UpdateUserProfile? = null,
    val error: String? = null
)


data class OnboardingState (
    val isLoading: Boolean = false,
    val success: OnboardingData? = null,
    val error: String? = null

)

