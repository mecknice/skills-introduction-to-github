package com.example.kalimani.presentation.Screens.search

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kalimani.presentation.navigation.OtherScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    val context = LocalContext.current
    val assetManager = context.assets
    val allVideoFiles = remember {
        // Get all video files from the 'videos' folder in assets
       // assetManager.list("videos")?.filter { it.endsWith(".mp4") } ?: emptyList()
        assetManager.list("mp4")?.filter { it.endsWith(".mp4") }?.map { it.trim() } ?: emptyList()
    }


    var searchQuery by remember { mutableStateOf("") }
    var filteredVideos by remember { mutableStateOf<List<String>>(emptyList()) }

    // Handle search button click
    fun onSearchClick() {
        filteredVideos = allVideoFiles.filter {
            it.contains(searchQuery, ignoreCase = true)
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
            ) {

        Text(
            text = "Kalimani",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Search TextField
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search ") },
            placeholder = { Text("Enter video name...") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Button
        Button(
            onClick = { onSearchClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF7043) // Orange color
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp)

        ) {
            Text("Search", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the filtered video list
        if (filteredVideos.isNotEmpty()) {
            LazyColumn {
                items(filteredVideos) { videoFile ->
                    VideoItem(videoFile,navController)
                }
            }
        } else {
            Text(
                "No videos found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun VideoItem(videoFile: String,navController: NavHostController) {
    val context = LocalContext.current
    val videoUri = remember { Uri.parse("file:///android_asset/mp4/$videoFile") }
    // Extracting the file name without the extension
    val fileNameWithoutExtension = videoFile.substringBeforeLast(".")

    Log.d("TAGG", "VideoItem:$videoFile ")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Video Title
            Text(
                text = fileNameWithoutExtension,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            // Play Icon Button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF7043)) // Orange background
                    .clickable { navController.navigate(OtherScreen.VideoPlayer.createRoute(videoFile))},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Video",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


//fun playVideo(context: Context, videoFile: String) {
//    val intent = Intent(Intent.ACTION_VIEW, videoFile).apply {
//        setDataAndType(videoUri, "video/*")
//    }
//    context.startActivity(intent)
//}
