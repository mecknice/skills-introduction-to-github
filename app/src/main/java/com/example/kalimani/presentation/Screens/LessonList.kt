package com.example.kalimani.presentation.Screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kalimani.R
import com.example.kalimani.presentation.navigation.OtherScreen
import java.net.URLEncoder


@Composable
fun LessonListScreen(
    category: String,
    navController: NavHostController
) {
    val context = LocalContext.current
    val assetManager = context.assets
    val fileList = assetManager.list("mp4")?.filter { it.endsWith(".mp4") }?.map { it.trim() } ?: emptyList()

   // Log.d("LessonListScreen", "File List: $fileList")

    val categorizedFiles = fileList.groupBy { fileName ->
        when {
            fileName.matches(Regex("^[A-Za-z]\\.mp4$")) -> "Alphabets"
            fileName.matches(Regex("^[A-Za-z]+\\.mp4$")) -> "Words"
            fileName.matches(Regex("^[0-9]+\\.mp4$")) -> "Numbers"
            else -> "Sign"  // Everything else
        }
    }


    //Log.d("LessonListScreen", "Categorized Files: $categorizedFiles")
    //Log.d("LessonListScreen", "Available keys: ${categorizedFiles["number"]}")

    // Normalize category for comparison
//    val normalizedCategory = category.lowercase()
//    val normalizedCategorizedFiles = categorizedFiles.mapKeys { it.key.lowercase() }
//    var filteredFiles = normalizedCategorizedFiles[normalizedCategory] ?: emptyList()
//    //Log.d("LessonListScreen", "Filtered Files for $category: $filteredFiles")
//
//    // Sort numbers in natural order if the category is "Numbers"
//    if (normalizedCategory == "numbers") {
//        filteredFiles = filteredFiles.sortedBy { fileName ->
//            fileName.substringBefore('.').toIntOrNull() ?: Int.MAX_VALUE // Parse the number or assign a high value for invalid names
//        }
//    }

    val normalizedCategory = category.lowercase()
    val normalizedCategorizedFiles = categorizedFiles.mapKeys { it.key.lowercase() }

    var filteredFiles = when (normalizedCategory) {
        "dictionary" -> {
            // Combine all categories in a systematic order
            listOf(
                "Alphabets" to (categorizedFiles["Alphabets"] ?: emptyList()),
                "Words" to (categorizedFiles["Words"] ?: emptyList()),
                "Numbers" to (categorizedFiles["Numbers"] ?: emptyList()),
                "Signs" to (categorizedFiles["Signs"] ?: emptyList())
            ).filter { it.second.isNotEmpty() }
        }
        else -> {
            listOf(category.capitalize() to (normalizedCategorizedFiles[normalizedCategory] ?: emptyList()))
        }
    }

    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        if (filteredFiles.isNotEmpty()) {
            filteredFiles.forEach { (title, files) ->
                item {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                }
                items(files) { fileName ->
                    Column(
                        Modifier
                            .padding(top = 2.dp)
                            .clickable {
                                val encodedPath = URLEncoder.encode(fileName, "UTF-8")
                                navController.navigate(OtherScreen.VideoPlayer.createRoute(encodedPath))
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = fileName.substringBeforeLast('.'),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = null
                            )
                        }
                        Divider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        } else {
            //Log.d("LessonListScreen", "No files found for category: $category")
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No lessons available for $category.",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
