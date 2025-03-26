package com.example.kalimani.presentation.Screens.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kalimani.domain.model.OnboardingData
import com.example.kalimani.presentation.viewModel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: AppViewModel= hiltViewModel(),
    userId: String,
    title: String ="",
    options: List<String>? = null, // Options for selectable screens
    imageResource: Int? = null,   // For image-based screen
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    onSkipClick: () -> Unit = {}
) {

    val onboardingState by viewModel.onboardingState.observeAsState()

    var selectedOption by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty Title */ },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            Modifier.size(20.dp).padding(bottom = 12.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF843014)
                ), modifier = Modifier.height(60.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (options != null) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(vertical = 8.dp)
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFFE85D3B),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = if (selectedOption == option) Color(0xFFFFD5C3) else Color(
                                    0xFFF6F6F6
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedOption = option }
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            color = if (selectedOption == option) Color(0xFFE85D3B) else Color.Black,
                            fontWeight = if (selectedOption == option) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            imageResource?.let {
                Spacer(modifier = Modifier.height(40.dp))
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Onboarding Image",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(modifier = Modifier.weight(0.7f))

            Button(
                onClick = {
                    if (selectedOption != null || imageResource != null) {
                        // Save onboarding data if options are provided
                        if (selectedOption != null) {
                            val data = when (title) {
                                "Which age group do you belong to?" -> OnboardingData(ageGroup = selectedOption)
                                "What is your Profession:" -> OnboardingData(profession = selectedOption)
                                "Do you have experience with Kiswahili Sign Language :" -> OnboardingData(
                                    experience = selectedOption
                                )

                                "What are your goals of using sign language?" -> OnboardingData(
                                    goals = selectedOption
                                )

                                "How did you find us?" -> OnboardingData(referralSource = selectedOption)
                                else -> null
                            }
                            data?.let { viewModel.saveOnboardingData(userId, it) }
                        }

                        onContinueClick()
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(57.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE85D3B))
            ) {
                Text(text = "Continue", color = Color.White, fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = onSkipClick) {
                Text(
                    text = "Skip",
                    color = Color(0xFFE85D3B),
                    fontWeight = FontWeight.Bold
                )
            }

            onboardingState?.let { state ->
                when {
                    state.isLoading -> CircularProgressIndicator()
                    state.error != null -> {
                        Text(text = "Error: ${state.error}", color = Color.Red)
                    }
                }
            }
        }
    }
}
