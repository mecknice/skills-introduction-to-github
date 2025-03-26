package com.example.kalimani.presentation.Screens.profile

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceInfoScreen(navController: NavHostController) {
    val deviceName = Build.MODEL
    val manufacturer = Build.MANUFACTURER
    val versionRelease = Build.VERSION.RELEASE
    val versionSdk = Build.VERSION.SDK_INT
    val appVersion = "1.0.0" // Replace with dynamic app version if needed

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
                        text = "Device Info",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your Device Information",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFE85D3B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Device Name
            InfoCard(
                title = "Device Name",
                value = deviceName,
                icon = Icons.Default.Smartphone
            )

            // Manufacturer
            InfoCard(
                title = "Manufacturer",
                value = manufacturer,
                icon = Icons.Default.Memory
            )

            // Android Version
            InfoCard(
                title = "Android Version",
                value = "Version $versionRelease (SDK $versionSdk)",
                icon = Icons.Default.DeviceHub
            )

            // App Version
            InfoCard(
                title = "App Version",
                value = appVersion,
                icon = Icons.Default.Info
            )
        }
    }
}

@Composable
fun InfoCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF1ED)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title Icon",
                tint = Color(0xFFE85D3B),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
