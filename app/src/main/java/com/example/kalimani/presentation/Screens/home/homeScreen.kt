package com.example.kalimani.presentation.Screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kalimani.R
import com.example.kalimani.presentation.Screens.Dashboard.LearningCard
import com.example.kalimani.presentation.viewModel.AppViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(
    //navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()) {


    val profileState by viewModel.profileState.observeAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid


    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile(userId!!)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // Top Section with wave background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFB3D9FF),  // Blue wave gradient color
                            Color.White
                        )
                    )
                )
        ) {
            // Circular Profile and Greeting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)  // Circular placeholder
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Hello! ${profileState?.success?.firstName}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "What would you like to\ndo today?",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kalimani Interpreter Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB3F2E0)),  // Light teal color
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                // Mock Icon

                    Image(painter = painterResource(id = R.drawable.appicon), contentDescription = null, modifier = Modifier
                        .size(50.dp))


                    Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Kalimani Interpreter",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Get instant meaning and\nunderstanding",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Continue Learning Section
        SectionHeader(title = "Continue learning")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
         //   LearningCard(title = "Alphabets", backgroundColor = Color(0xFFFFF2E6), onClick = {})
           // LearningCard(title = "Numbers", backgroundColor = Color(0xFFE6FFE6),R.drawable.quiz, onClick = {})
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Words Section
        SectionHeader(title = "Featured Words")
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB3F2E0)),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                // Mock Icon

                Image(
                    painter = painterResource(id = R.drawable.number),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )


                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Quiz of the day.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Practice your kalimani skills",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// Reusable Section Header with See All
@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "See All",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}
