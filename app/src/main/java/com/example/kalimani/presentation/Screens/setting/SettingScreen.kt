package com.example.kalimani.presentation.Screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kalimani.presentation.navigation.OtherScreen
import com.example.kalimani.presentation.navigation.Screen
import com.example.kalimani.presentation.viewModel.AppViewModel


@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: AppViewModel= hiltViewModel()
    ) {



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(top = 16.dp)
    ) {
        // Account Section
        SettingItem(
            icon = Icons.Outlined.Person,
            title = "My Account",
            subtitle = "Make changes to your account",
            warningIcon = true,
            onClick = {navController.navigate(OtherScreen.ProfileUpdate.route)}
        )
        SettingItem(
            icon = Icons.Outlined.ThumbUp,
            title = "Thank you",
            subtitle = "Donate",
            onClick = {navController.navigate(OtherScreen.ThankYou.route)}
        )
        SettingItem(
            icon = Icons.Outlined.Language,
            title = "Language",
            subtitle = "Select your language",
            onClick = {navController.navigate(OtherScreen.Language.route)}

            //onClick = { showLogoutDialog = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Help & Support Section
        Surface(
            modifier = Modifier
                //    .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            //elevation = 4.dp
        ) {
            Column {
                SettingItem(
                    icon = Icons.Default.Help,
                    title = "Help & Support",
                    subtitle = "",
                    onClick = {navController.navigate(OtherScreen.HelpAndSupport.route)}
                )
                //Divider()
                SettingItem(
                    icon = Icons.Default.Info,
                    title = "About App",
                    subtitle = "",
                    onClick = {navController.navigate(OtherScreen.AboutApp.route)}
                )
            }
        }
    }



}


@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    warningIcon: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .background(
                    Color(0xFFEDE7F6),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(22.dp),
                tint = Color(0xFFFF7043) // Customize icon tint color
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        if (warningIcon) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                tint = Color.Red
            )
        } else {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next",
                tint = Color.Gray
            )
        }
    }
//    Divider()
}
