package com.example.kalimani.presentation.Screens.auth


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.compose.rememberNavController
import com.example.kalimani.GoogleSignInUtils
import com.example.kalimani.R
import com.example.kalimani.presentation.Screens.Global.LoadingIndicator
import com.example.kalimani.presentation.navigation.OnboardingScreens
import com.example.kalimani.presentation.navigation.OtherScreen
import com.example.kalimani.presentation.navigation.Screen
import com.example.kalimani.presentation.viewModel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val googleSignInClient = remember { createGoogleSignInClient(context) }
    val loginState by viewModel.loginState.observeAsState()
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        GoogleSignInUtils.doGoogleSignIn(
            context = context,
            scope = scope,
            launcher = null,
            login = {
                Toast.makeText(context, "Signin With Google successful", Toast.LENGTH_SHORT).show()
            }
        )
    }

    BackHandler {
        (context as? Activity)?.finish()
    }

    when {
        loginState?.isLoading == true -> {
            LoadingIndicator()
        }

        loginState?.success != null -> {
            LaunchedEffect(Unit) {
                navController.navigate(OnboardingScreens.AgeGroup.route) {
                    popUpTo(OtherScreen.Signin.route) { inclusive = true }
                }
            }
            Toast.makeText(context, "Login Successfully!", Toast.LENGTH_SHORT).show()
            loginState!!.success = null
        }

        loginState?.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${loginState?.error}")
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFF843014))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(21.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.7f))

                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

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
                .padding(horizontal = 16.dp, vertical = 16.dp), // Added bottom padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))


            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "For free, join now and\nstart learning",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            TextFieldWithPlaceholderss(label = "Email", placeholder = "Enter Email", value = Email) { Email = it }
            Spacer(modifier = Modifier.height(18.dp))
            TextFieldWithPlaceholderss(label = "Password", placeholder = "Enter Password", value = Password) { Password = it }
            Spacer(modifier = Modifier.height(7.dp))
            Box(modifier = Modifier.fillMaxWidth().align(Alignment.End)) {
                Text(
                    text = "Forget Password",
                    color = Color.Red,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { navController.navigate(OtherScreen.ForgetPassword.route) }
                )
            }

            Spacer(modifier = Modifier.weight(0.3f)) // Pushes content to fit screen
            Button(
                onClick = { viewModel.login(email = Email, password = Password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE85D3B)),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (loginState?.isLoading == true) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Login", color = Color.White, fontSize = 16.sp)
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
                        Log.d("GoogleSignIn", "Attempting Google Sign-In")
                        GoogleSignInUtils.doGoogleSignIn(
                            context = context,
                            scope = scope,
                            launcher = launcher,
                            login = {
                                Log.d("GoogleSignIn", "Google Sign-In Success")
                                navController.navigate(OnboardingScreens.AgeGroup.route) {
                                    popUpTo("signup") { inclusive = true }
                                }
                                Toast.makeText(
                                    context,
                                    "Signin With Google successful",
                                    Toast.LENGTH_SHORT
                                ).show()
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

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Not a member? ",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Signup",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE85D3B),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(OtherScreen.Signup.route) {
                            popUpTo(OtherScreen.Signin.route) { inclusive = true }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Bottom padding
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithPlaceholderss(label: String, value: String,placeholder: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
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
                cursorColor = Color.Black
            ),
            placeholder = { Text(text = placeholder, color = Color.Gray) }
        )
    }
}


