package com.example.kalimani.presentation.Screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalimani.R
import kotlinx.coroutines.delay


//@Preview
@Composable
fun SplashScreen( onRegisterClick: () -> Unit ={}, onBackClick: () -> Unit={}) {
    var animationPlayed by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }

    // Launch the animation once
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
        delay(800)
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(220.dp))
            Image(
                painter = painterResource(id = R.drawable.appicon), // Replace with your logo
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .scale(if (!animationPlayed) scale.value else 1f)
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Kalimani",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )





            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



