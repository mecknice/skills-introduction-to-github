package com.example.kalimani.presentation.Screens.profile

import android.util.Log
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kalimani.R
import com.example.kalimani.presentation.Screens.Global.LoadingIndicator
import com.example.kalimani.presentation.navigation.OtherScreen
import com.example.kalimani.presentation.navigation.Screen
import com.example.kalimani.presentation.viewModel.AppViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: AppViewModel= hiltViewModel(),
    onEditProfileClick: () -> Unit={ navController.navigate(OtherScreen.ProfileUpdate.route)},
    onSettingItemClick: (String) -> Unit={}
) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    val profileState by viewModel.profileState.observeAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid


    LaunchedEffect(userId) {
        userId?.let {
            viewModel.fetchUserProfile(it)
        }
    }




    val profile = profileState?.success

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    // Wavy Shape
                 Image(painter = painterResource(id = R.drawable.topprofile),
                     contentDescription = null,
                     contentScale = ContentScale.FillBounds,
                     modifier = Modifier.fillMaxSize()

                 )

                    // Profile Details
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Picture with Edit Icon
                        Box {
                            CircleImage()
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit Profile",
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(Color(0xFFE85D3B), CircleShape)
                                    .padding(4.dp)
                                    .clickable { onEditProfileClick() },
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Hey, ${profileState?.success?.firstName ?: "Buddy"}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(1.dp))
                            Icon(
                                imageVector = Icons.Outlined.Edit, // Replace with your edit icon
                                contentDescription = "Edit Profile",
                                modifier = Modifier
                                    .size(14.dp)
                                    .background(Color(0xFFE85D3B), CircleShape)
                                    .padding(4.dp)
                                    .clickable { onEditProfileClick() },
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Welcome to your profile!",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Settings List
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LogOutItem(
                        icon = Icons.Outlined.Devices,
                        title = "Devices",
                        subtitle = "",
                        onClick = { navController.navigate(OtherScreen.DeviceInfo.route) }
                    )
                    LogOutItem(
                        icon = Icons.Outlined.Notifications,
                        title = "Notifications",
                        subtitle = "",
                        onClick = { onSettingItemClick("Notifications") }
                    )

                    //SettingItem(title = "Devices", onClick = { navController.navigate(OtherScreen.DeviceInfo.route) })
//                    SettingItem(
//                        title = "Notifications",
//                        onClick = { onSettingItemClick("Notifications") })
                   // SettingItem(title = "Language", onClick = {navController.navigate(OtherScreen.Language.route)})
                    LogOutItem(
                        icon = Icons.Outlined.Logout,
                        title = "LogOut",
                        subtitle = "",
                        onClick = { showLogoutDialog = true }
                    )
                }

            }
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false }, // Close dialog if dismissed
            title = { Text("Log Out") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.logOut()
                    showLogoutDialog = false
                    navController.navigate(OtherScreen.Signin.route) { // Navigate to Login Screen
                        popUpTo(Screen.Setting.route) { inclusive = true } // Clear back stack
                    }
                }) {
                    Text("Log Out", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }


    }




@Composable
fun CircleImage() {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.2f)) // Placeholder for the profile picture
    ){
        Image(painter =
        painterResource(id = R.drawable.profile_img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

        )
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos, // Replace with your forward arrow icon
            contentDescription = null,
            modifier = Modifier.size(19.dp),
            tint = Color.Gray
        )
    }
}

@Composable
fun LogOutItem(
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