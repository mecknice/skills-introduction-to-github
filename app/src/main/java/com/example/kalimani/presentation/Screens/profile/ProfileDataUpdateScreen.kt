package com.example.kalimani.presentation.Screens.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kalimani.R
import com.example.kalimani.domain.model.UpdateUserProfile
import com.example.kalimani.presentation.viewModel.AppViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileDataUpdateScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {
        ProfileContent(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBanSection() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /* Handle back action */ },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        modifier = Modifier.fillMaxWidth() // Ensure no extra padding or margin
    )
}


@Composable
fun ProfileContent(modifier: Modifier = Modifier, viewModel: AppViewModel = hiltViewModel()) {

    val context = LocalContext.current



    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable {mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    val updateState by viewModel.updateState.observeAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var editProfile by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(updateState) {
        updateState?.let {
            if (it.success != null) {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                editProfile = false // Exit edit mode after update
            } else if (it.error != null) {
                Toast.makeText(context, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }


            if (!editProfile) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Profile image section with change photo option
                    ProfileImageSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section header for basic information
                    Text(
                        text = "Basic Information",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    var name = "$firstName $lastName"

                    // Profile information items
                    ProfileItem(label = "Name", value = name)
                    ProfileItem(label = "Country", value = country)
                    ProfileItem(label = "Email", value = email)
                    ProfileItem(label = "PhoneNumber", value = phoneNumber)


                    Spacer(modifier = Modifier.height(32.dp))

                    // Save changes button with custom styling
                    Button(
                        onClick = { editProfile = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text(text = "Edit Profile", color = Color.White, fontSize = 16.sp)
                    }
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Profile image section with change photo option
                    ProfileImageSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section header for basic information
                    Text(
                        text = "Basic Information",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    customTextField(
                        firstName, "First Name",
                        onValueChange = { firstName = it }
                    )
                    Spacer(Modifier.height(6.dp))

                    customTextField(
                        lastName, "Last Name",
                        onValueChange = { lastName = it }
                    )

                    Spacer(Modifier.height(6.dp))
                    customTextField(
                        country, "Country",
                        onValueChange = {country = it}
                    )

                    Spacer(Modifier.height(6.dp))
//                    bio =
                    customTextField(
                        phoneNumber, "PhoneNumber",
                        onValueChange = {phoneNumber = it}
                    )

                    Spacer(modifier = Modifier.height(32.dp))


                    Button(
                        onClick = {

                            val updatedProfile = UpdateUserProfile(
                                firstName = firstName,
                                lastName = lastName,
                                country = country,
                                email = email,
                                phoneNumber = phoneNumber
                            )
                            viewModel.updateUserProfile(userId!!, updatedProfile)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text(text = "Save Changes", color = Color.White, fontSize = 16.sp)
                    }


                }
            }

    }



// Profile image section with a placeholder image and option to change profile photo.
@Composable
fun ProfileImageSection(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .border(2.dp, Color.LightGray, CircleShape)
                .clickable(enabled = isEditing) { onClick?.invoke() },

        ) {

                Image(
                    painter = painterResource(id = R.drawable.profile_img),
                    contentDescription = "Default Profile",
                    modifier = Modifier.size(100.dp)
                )

        }
    }

}



// Composable to display an individual profile item with a label, value, and arrow icon.
@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { /* Handle item click */ },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        // Label on the left
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Value and arrow icon on the right
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTextField(
    value: String,
    placeHolder: String,
    onValueChange: (String) -> Unit
) {
    var editableText by rememberSaveable { mutableStateOf(value) }

    TextField(
        value = editableText,
        onValueChange = {
            editableText = it
            onValueChange(it)
        },
        placeholder = { Text(placeHolder, fontWeight = FontWeight.Medium) },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            disabledIndicatorColor = Color.Transparent
        )
    )


}