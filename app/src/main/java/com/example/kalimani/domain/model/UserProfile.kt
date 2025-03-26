package com.example.kalimani.domain.model

data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = ""
)


data class UpdateUserProfile(
    val firstName: String? = "",
    val lastName: String? = "",
    val phoneNumber: String? = "",
    val country : String= "",
    val email: String = "",
)