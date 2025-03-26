package com.example.kalimani.presentation.Screens.auth

import androidx.compose.foundation.background
import com.example.kalimani.presentation.viewModel.AppViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kalimani.presentation.navigation.OtherScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }


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
                            text = "Forgot Password",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.sendPasswordResetEmail(email) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE85D3B) // Orange color
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Send Reset Email")
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (uiState) {
                    is AppViewModel.ForgotPasswordState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is AppViewModel.ForgotPasswordState.Success -> {
                        Text(
                            text = (uiState as AppViewModel.ForgotPasswordState.Success).message,
                            color = MaterialTheme.colorScheme.primary
                        )
                        navController.navigate(OtherScreen.Signin.route){
                            popUpTo(OtherScreen.ForgetPassword.route){
                                inclusive = true
                            }
                        }
                    }
                    is AppViewModel.ForgotPasswordState.Failure -> {
                        Text(
                            text = (uiState as AppViewModel.ForgotPasswordState.Failure).error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    AppViewModel.ForgotPasswordState.Idle -> Unit
                    else -> {}
                }
            }
        }
    }
}
