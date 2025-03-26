package com.example.kalimani.presentation.Screens.learning

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kalimani.R

data class Category(
    val id: Int,
    //val title: String,
    val icon: Int,
    val backgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningHomeScreen(
   // navController: NavController,
    userName: String = "Orely Studio"
) {
    val categories = listOf(
        Category(1,  R.drawable.appicon, Color(0xFFFFF3E0)),
        Category(2,  R.drawable.appicon, Color(0xFFE8F5E9)),
        Category(3, R.drawable.appicon, Color(0xFFE3F2FD)),
        Category(4,  R.drawable.appicon, Color(0xFFFCE4EC))
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple(Icons.Default.Home, "Home", true),
                    Triple(Icons.Default.Search, "Explore", false),
                    Triple(Icons.Default.DateRange, "Quiz", false),
                    Triple(Icons.Default.Bookmark, "Bookmark", false),
                    Triple(Icons.Default.Person, "Profile", false)
                )
                items.forEach { (icon, label, selected) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = selected,
                        onClick = { }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Header with Greeting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF90EE90).copy(alpha = 0.3f),
                                Color(0xFFE0FFE0).copy(alpha = 0.1f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hi, $userName",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Let's learn something new today!",
                            color = Color.DarkGray,
                            fontSize = 14.sp
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Learning Categories
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { category ->
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .clickable { },
                        colors = CardDefaults.cardColors(containerColor = category.backgroundColor)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = category.icon),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
//                                    .size(60.dp)
//                                    .padding(8.dp)
                            )
//                            Text(
//                                text = category.title,
//                                fontWeight = FontWeight.Medium,
//                                fontSize = 14.sp
//                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Learning Path Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Learning path",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "New",
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = "See All",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Learning Path Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Birds Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                        .clickable { },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.appicon),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )
                        Text(
                            text = "Learn about\ndifferent types\nof birds",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                }

                // Mammals Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                        .clickable { },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.appicon),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )
                        Text(
                            text = "Learn about\nthe types of\nmammals",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
