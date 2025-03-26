package com.example.kalimani.presentation.Screens.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

 fun createGoogleSignInClient(context: Context) = GoogleSignIn.getClient(
    context,
    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("204973107359-qpje0ot5lhjffl3esvbn5tkokvcia4tj.apps.googleusercontent.com") // Web Client ID
        .requestEmail()
        //.setPrompt("select_account") // Forces account selection
        .build()
)
