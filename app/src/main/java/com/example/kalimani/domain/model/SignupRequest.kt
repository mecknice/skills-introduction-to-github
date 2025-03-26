package com.example.kalimani.domain.model

data class SignupRequest(
    val firstName: String?, // Nullable field
    val lastName: String?,  // Nullable field
    val email: String,
    val password: String
)
