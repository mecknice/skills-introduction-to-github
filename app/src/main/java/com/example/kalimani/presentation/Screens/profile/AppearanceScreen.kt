package com.example.kalimani.presentation.Screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    navController: NavHostController,
    isDarkTheme: Boolean = true ,
    onThemeChange: (Boolean) -> Unit={},
    onFontSizeChange: (Float) -> Unit={}
) {
    var currentFontSize by remember { mutableStateOf(16f) }
    var isDarkModeEnabled by remember { mutableStateOf(isDarkTheme) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Appearance",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE85D3B) // Orange color
                ), modifier = Modifier.height(64.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Theme Selection Row
            Text(
                text = "Theme",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF1ED), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isDarkModeEnabled) Icons.Default.Brightness4 else Icons.Default.Brightness7,
                        contentDescription = "Theme Icon",
                        tint = Color(0xFFE85D3B),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isDarkModeEnabled) "Dark Mode" else "Light Mode",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Switch(
                    checked = isDarkModeEnabled,
                    onCheckedChange = {
                        isDarkModeEnabled = it
                        onThemeChange(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFFE85D3B),
                        checkedThumbColor = Color.White
                    )
                )
            }

            // Font Size Selector
            Text(
                text = "Font Size",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDF1ED), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TextFields,
                            contentDescription = "Font Size Icon",
                            tint = Color(0xFFE85D3B),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Adjust Font Size",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Slider(
                    value = currentFontSize,
                    onValueChange = {
                        currentFontSize = it
                        onFontSizeChange(it)
                    },
                    valueRange = 12f..24f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFE85D3B),
                        activeTrackColor = Color(0xFFE85D3B)
                    )
                )
                Text(
                    text = "Current Size: ${currentFontSize.toInt()}sp",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
