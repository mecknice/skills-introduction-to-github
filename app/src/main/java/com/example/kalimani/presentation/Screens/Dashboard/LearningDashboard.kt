package com.example.kalimani.presentation.Screens.Dashboard

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kalimani.R
import com.example.kalimani.presentation.Screens.learning.Category
import com.example.kalimani.presentation.navigation.OtherScreen
import com.example.kalimani.presentation.viewModel.AppViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LearningDashboard(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
    ) {

    val profileState by viewModel.profileState.observeAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid


    LaunchedEffect(userId) {
        userId?.let {
            viewModel.fetchUserProfile(it)
        }
    }




    Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {

            DoubleBackToExit()

            // Top Section with wave background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)


            ) {
                Image(
                    painter = painterResource(id = R.drawable.cloud),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer(
                            alpha = 0.65f
                        )

                )
                // Circular Profile and Greeting
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {

                        Box(
                            modifier = Modifier
                                .size(55.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE85D3B)), // Background color for the circle
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = profileState?.success?.firstName?.firstOrNull()?.uppercase() ?: "B",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .background(Color.Gray.copy(alpha = 0.1f)) // Light transparent background color

                                .padding(5.dp)
                        ) {
                            Text(
                                text = "Hi, ${profileState?.success?.firstName ?: "Buddy"}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }


                        Box(
                            modifier = Modifier
                                .background(Color.Gray.copy(alpha = 0.1f)) // Light transparent background color
                                .padding(5.dp)
                        ) {
                            Text(
                                text = "Let's learn something new today!",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }


//                    IconButton(
//                        onClick = { },
//                        modifier = Modifier
//                            .size(40.dp)
//                            .clip(CircleShape)
//                            .background(Color.White)
//                    ) {
//                        Icon(Icons.Default.Person, contentDescription = "Profile")
//                    }
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clip(CircleShape)
//                            .background(Color.LightGray)  // Circular placeholder
//                    ){
//                        Image(painter =
//                        painterResource(id = R.drawable.profile_img),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Column {
//                        Text(
//                            text = "Hello! ${profileState?.success?.firstName}",
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = "What would you like to\ndo today?",
//                            fontSize = 14.sp,
//                            color = Color.Black
//                        )
//                    }

//            data class Category(
//                val id: Int,
//                //val title: String,
//                val icon: Int,
//               // val backgroundColor: Color
//            )
//
//
//            val categories = listOf(
//                Category(1, R.drawable.mamme),
//                Category(2,  R.drawable.words),
//                Category(3,  R.drawable.num),
//                Category(4,  R.drawable.abcd)
//            )
//
//            // Learning Categories
//            LazyRow(
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(categories) { category ->
//                    Card(
//                        modifier = Modifier
//                            .width(100.dp)
//                            .height(80.dp)
//                            .clickable { },
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier.padding(5.dp)
//                        ) {
//                            Image(
//                                painter = painterResource(id = category.icon),
//                                contentDescription = null,
//                                contentScale = ContentScale.FillBounds,
//                               // modifier = Modifier.fillMaxSize()
////                                    .size(60.dp)
////                                    .padding(8.dp)
//                            )
//
//                            Text(
//                                text = "Start Learning",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold
//                            )
////                            Text(
////                                text = category.title,
////                                fontWeight = FontWeight.Medium,
////                                fontSize = 14.sp
////                            )
//                        }
//                    }
//                }
//            }




            JengaText()

        // Grid Section
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LearningCard("Alphabets","Alphabets Lessons",R.drawable.alphabet, Color(0xFFFFF3E0),
                    onClick = {navController.navigate("lesson_list/alphabets")})
                LearningCard("Numbers","Number Lessons",R.drawable.number, Color(0xFFE8F5E9),
                    onClick = {navController.navigate("lesson_list/numbers")})
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LearningCard("Words","Words Lessons",R.drawable.comment, Color(0xFFFFF3E0),
                    onClick = {navController.navigate("lesson_list/words")})
                LearningCard("Sign","No Lessons",R.drawable.signpost, Color(0xFFFFF3E0),
                    onClick = {navController.navigate("lesson_list/signs")})
            }
            // Dictionary Button
            Button(
                onClick = { navController.navigate("lesson_list/dictionary") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE85D3B)) // Purple color
            ) {
                Text(text = "Dictionary", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}



@Composable
fun LearningCard(title: String,lessons: String, imageResId: Int, backgroundColor: Color,onClick: ()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp) // Overall padding
    ) {
        // Set a fixed width or use fillMaxWidth() to ensure the button and card have the same width
        val cardWidth = 150.dp  // Define a consistent width

        // Card Section
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .width(cardWidth)
                .height(140.dp)  // Set width
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape) // Optional: Clip the image into a circular shape
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = lessons ,
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Add space between the card and the button
        Spacer(modifier = Modifier.height(12.dp))

        // Button Section with the same width
        Button(
            onClick = {onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(cardWidth)  // Set the same width as the card
        ) {
            Text(text = title, color = Color.White)
        }
    }
}



@Composable
fun CustomCircularProgress(progress: Float, progressColor: Color) {
    Canvas(modifier = Modifier.size(60.dp)) {
        val strokeWidth = 6.dp.toPx()
        // Draw the background circle (gray)
        drawCircle(
            color = Color.White,
            style = Stroke(width = strokeWidth)
        )
        // Draw the progress arc
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLearningDashboard() {
    LearningDashboard(navController = rememberNavController())
}

@Composable
fun DoubleBackToExit() {
    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        if (backPressedOnce) {
            // Exit the app if back is pressed twice
            (context as? Activity)?.finishAffinity()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()

            // Reset the back press flag after 2 seconds
            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }
}

@Composable
fun JengaText() {
    val letters = listOf("J", "E", "N", "G", "A")
    val colors = listOf(
        Color(0xFFFF5733),  // Vibrant Orange
        Color(0xFF33FF57),  // Greenish
        Color(0xFF143AF6),  // Blue
        Color(0xFFFF33A8),  // Pinkish
        Color(0xFFFFD700)   // Gold
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        letters.forEachIndexed { index, letter ->
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)   // Ensures even distribution
                    .aspectRatio(1f) // Keeps it square
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                    .background(Color.DarkGray, shape = CircleShape)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter,
                    fontSize = 42.sp, // Adjust based on screen
                    fontWeight = FontWeight.Bold,
                    color = colors[index],
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}




//@Composable
//fun JengaText() {
//    val letters = listOf("J", "E", "N", "G", "A")
//    val colors = listOf(
//        Color(0xFFFF5733),  // Vibrant Orange
//        Color(0xFF33FF57),  // Greenish
//        Color(0xFF3357FF),  // Blue
//        Color(0xFFFF33A8),  // Pinkish
//        Color(0xFFFFD700)   // Gold
//    )
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .background(Color.LightGray),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        letters.forEachIndexed { index, letter ->
//            Text(
//                text = letter,
//                fontSize = 48.sp,
//                fontWeight = FontWeight.Bold,
//                color = colors[index],
//                modifier = Modifier
//                    .padding(8.dp)
//                    .shadow(8.dp, shape = RoundedCornerShape(12.dp))
//                    .background(Color.DarkGray, shape = CircleShape)
//                    .padding(16.dp)
//            )
//        }
//    }
//}


