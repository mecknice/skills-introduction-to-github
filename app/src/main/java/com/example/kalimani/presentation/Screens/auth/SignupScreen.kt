package com.example.kalimani.presentation.Screens.auth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kalimani.GoogleSignInUtils
import com.example.kalimani.R
import com.example.kalimani.domain.model.SignupRequest
import com.example.kalimani.presentation.Screens.Global.LoadingIndicator
import com.example.kalimani.presentation.Screens.onBoarding.OnboardingScreen
import com.example.kalimani.presentation.navigation.OnboardingScreens
import com.example.kalimani.presentation.navigation.OtherScreen
import com.example.kalimani.presentation.navigation.Screen
import com.example.kalimani.presentation.viewModel.AppViewModel


@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val signupState by viewModel.signUpState.observeAsState()
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        GoogleSignInUtils.doGoogleSignIn(
            context = context,
            scope = scope,
            launcher = null,
            login = {
                Toast.makeText(context, "Signup With Google successful", Toast.LENGTH_SHORT).show()
            }
        )

    }

    BackHandler {
        // Exiting the app
        (context as? Activity)?.finish()
    }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    when {



        signupState?.success != null -> {
            Text("Login Successful: ${signupState?.success}")
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(OtherScreen.Signin.route) { inclusive = true }
                }
            }
            Toast.makeText(context, "Signup Successfully!", Toast.LENGTH_SHORT).show()
        }

        signupState?.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${signupState?.error}")
            }

            Toast.makeText(context, "Something Went Wrong ${signupState!!.error}", Toast.LENGTH_SHORT).show()

            Log.d("problem","mdjdj ${signupState!!.error}")
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFF843014)) // Background color
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Navigation Icon at the Start
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(21.dp),
                            tint = Color.White
                        )
                    }

                    // Spacer to push text to the center
                    Spacer(modifier = Modifier.weight(0.7f))

                    // Title Text in the Center
                    Text(
                        text = "Signup",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically) // Center text vertically within the top bar
                    )

                    // Spacer to balance layout
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create an Account",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TextFieldWithPlaceholder(label = "First Name", placeholderText = "Enter FirstName", value = firstName) { firstName = it }
                TextFieldWithPlaceholder(label = "Last Name", placeholderText = "Enter LastName", value = lastName) { lastName = it }
                TextFieldWithPlaceholder(label = "Email Email", placeholderText = "Enter Email", value = email) { email = it }
                TextFieldWithPlaceholder(label = "Enter Password", placeholderText = "Enter Password", value = password) { password = it }
             }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val signupRequest = SignupRequest(
                        firstName = firstName.takeIf { it.isNotEmpty() },
                        lastName = lastName.takeIf { it.isNotEmpty() },
                        email = email,
                        password = password
                    )
                    viewModel.signUp(signupRequest)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE85D3B)),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (signupState  != null) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Continue", color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
            Text(
                text = "Or",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .alpha(0.5f)
            )
            Divider(modifier = Modifier.weight(1f), color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
        }

        Spacer(modifier = Modifier.height(10.dp))

// "Do it later" text
        Text(
            text = "Do it later",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE85D3B),
            //textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(Screen.Home.route) {
                    popUpTo(OtherScreen.Signup.route) { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(14.dp)) // Adjusted spacing


        Spacer(modifier = Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .size(height = 40.dp, width = 100.dp)
                        .clickable {
                            GoogleSignInUtils.doGoogleSignIn(
                                context = context,
                                scope = scope,
                                launcher = launcher,
                                login = {
                                    navController.navigate(OnboardingScreens.AgeGroup.route) {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                    Toast
                                        .makeText(
                                            context,
                                            "Signup With Google successful",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            )
                        }
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.size(24.dp)
                    )
                }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already a member? ",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE85D3B),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { navController.navigate(OtherScreen.Signin.route){
                        popUpTo(OtherScreen.Signup.route) { inclusive = true }
                    } }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithPlaceholder(label: String, value: String,placeholderText: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF1F1F1)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = VisualTransformation.None,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFF1F1F1),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorContainerColor = Color.Black
            ),
            placeholder = { Text(text = placeholderText, color = Color.Gray) }
        )
    }
}

@Composable
fun SocialLoginButton(iconResId: Int, contentDescription: String, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .background(Color(0xFFF1F1F1), shape = RoundedCornerShape(8.dp))
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = Color.Black
        )
    }
}
