package com.example.kalimani.presentation.Screens.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kalimani.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndSupportScreen(navController: NavHostController) {
    val context = LocalContext.current

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
                        text = "Help & Customer Support",
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(16.dp))

                // Heading
                Text(
                    text = "Need Assistance?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Our team is here to help you!",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Call Section
                SupportButton(
                    title = "Call Us",
                    iconRes = android.R.drawable.ic_menu_call,
                    onClick = {
                        val callIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:+1234567890")
                        }
                        context.startActivity(callIntent)
                    }
                )

                // Chat Section
                SupportButton(
                    title = "Chat with Us",
                    iconRes = android.R.drawable.sym_action_chat,
                    onClick = {
                        val chatIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("sms:+1234567890")
                        }
                        context.startActivity(chatIntent)
                    }
                )

                // Mail Section
                SupportButton(
                    title = "Email Us",
                    iconRes = android.R.drawable.sym_action_email,
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@example.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Customer Support")
                        }
                        context.startActivity(emailIntent)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Social Media Section
                Text(
                    text = "Connect with us on Social Media",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SocialMediaButton("Facebook", "https://facebook.com", R.drawable.instagram, context)
                    SocialMediaButton("Instagram", "https://instagram.com",R.drawable.facebook, context)
                    SocialMediaButton("Twitter", "https://twitter.com",R.drawable.twitter, context)
                }
            }
        }
    }
}

@Composable
fun SupportButton(title: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE85D3B))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SocialMediaButton(platform: String, url: String, iconRes: Int, context: android.content.Context) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color(0xFFE85D3B), shape = CircleShape)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                val socialIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(socialIntent)
            }
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = platform,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
