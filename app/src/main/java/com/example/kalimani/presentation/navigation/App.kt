package com.example.kalimani.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kalimani.Preferences.UserPreferences
import com.example.kalimani.R
import com.example.kalimani.presentation.Screens.Dashboard.LearningDashboard
import com.example.kalimani.presentation.Screens.LessonListScreen
import com.example.kalimani.presentation.Screens.SplashScreen
import com.example.kalimani.presentation.Screens.VideoPlayerScreen
import com.example.kalimani.presentation.Screens.auth.SignUpScreen
import com.example.kalimani.presentation.Screens.auth.SigninScreen
import com.example.kalimani.presentation.Screens.home.HomeScreen
import com.example.kalimani.presentation.Screens.onBoarding.OnboardingScreen
import com.example.kalimani.presentation.Screens.profile.AppearanceScreen
import com.example.kalimani.presentation.Screens.profile.DeviceInfoScreen
import com.example.kalimani.presentation.Screens.profile.LanguageScreen
import com.example.kalimani.presentation.Screens.profile.ProfileDataUpdateScreen
import com.example.kalimani.presentation.Screens.profile.ProfileScreen
import com.example.kalimani.presentation.Screens.setting.AboutAppScreen
import com.example.kalimani.presentation.Screens.setting.HelpAndSupportScreen
import com.example.kalimani.presentation.Screens.setting.SettingsScreen
import com.example.kalimani.presentation.Screens.setting.ThankYouScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


@Composable
fun App() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var showSplash by remember { mutableStateOf(true) }
    var isOnboarded by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    val userPreferences = UserPreferences(context)
    val currentUser = FirebaseAuth.getInstance().currentUser


    LaunchedEffect(Unit) {
        isOnboarded = userPreferences.isOnboarded.first()
        isLoggedIn = userPreferences.isLoggedIn.first()
        delay(2000)
        showSplash = false


    }
}



    // Check if the user is logged in and has completed onboarding
   // val isOnboarded = userPreferences.isOnboarded.collectAsState(initial = false)
  //  val isLoggedIn = userPreferences.isLoggedIn.collectAsState(initial = false)
//
//    LaunchedEffect(Unit) {
//        val firebaseUser = FirebaseAuth.getInstance().currentUser
//        isLoggedIn.value = firebaseUser != null
//       // isOnboarded.value = userPreferences.isOnboarded()
//        showSplash.value = true
//        isLoading.value = false
//    }
//
//    val startDestination = when {
//        showSplash.value -> "Splash"
//        !isOnboarded.value -> OnboardingScreens.AgeGroup.route
//        isLoggedIn.value -> Screen.Home.route
//        else -> OtherScreen.Signin.route
//    }

//    if (isLoading.value) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//
//        }
//
//    } else {
//
//        val screens = listOf(
//            Screen.Learn,
//            Screen.Home,
//            Screen.Setting,
//            Screen.Profile
//        )
//
//        // List of routes where the BottomNavigationBar should be hidden
//        val screensWithoutBottomBar = listOf(
//            OtherScreen.Signin.route,
//            OtherScreen.Signup.route,
//            OtherScreen.VideoPlayer.route,
//            OtherScreen.LessonList.route,
//            OtherScreen.ProfileUpdate.route,
//            OtherScreen.ThankYou.route,
//            OtherScreen.HelpAndSupport.route,
//            OtherScreen.AboutApp.route,
//            OtherScreen.Appearance.route,
//            OtherScreen.DeviceInfo.route,
//            OtherScreen.Language.route,
//            OtherScreen.Splash.route,
//            OnboardingScreens.Find.route,
//            OnboardingScreens.AgeGroup.route,
//            OnboardingScreens.Profession.route,
//            OnboardingScreens.Experience.route,
//            OnboardingScreens.Goals.route,
//            OnboardingScreens.Find.route,
//            OnboardingScreens.FinalScreen.route,
//        )
//
//        Scaffold(
//            bottomBar = {
//                val currentDestination =
//                    navController.currentBackStackEntryAsState().value?.destination
//                if (currentDestination?.route !in screensWithoutBottomBar) {
//                    BottomNavigationBar(navController = navController, screens = screens)
//
//                }
//            }
//        ) { innerPadding ->
//            Box(modifier = Modifier.padding(innerPadding)) {
//                NavHost(
//                    navController = navController, startDestination = startDestination.toString()
//                ) {
//                    composable(Screen.Learn.route) { LearningDashboard(navController) }
//                    composable(Screen.Home.route) { HomeScreen(navController) }
//                    composable(Screen.Setting.route) { SettingsScreen(navController) }
//                    composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
//
//
//
//                    composable(OtherScreen.Signin.route) { SigninScreen(navController) }
//                    composable(OtherScreen.Signup.route) { SignUpScreen(navController) }
//                    // composable(OtherScreen.Lesson.route) { ListScreen(navController) }
//                    composable(OtherScreen.ProfileUpdate.route) {
//                        ProfileDataUpdateScreen(
//                            navController
//                        )
//                    }
//                    composable(
//                        OtherScreen.LessonList.route,
//                        arguments = listOf(navArgument("category") { type = NavType.StringType })
//                    ) { backStackEntry ->
//                        val category = backStackEntry.arguments?.getString("category") ?: ""
//                        LessonListScreen(navController = navController, category = category)
//                    }
//
//                    composable(OtherScreen.AboutApp.route) { AboutAppScreen(navController) }
//                    composable(OtherScreen.HelpAndSupport.route) {
//                        HelpAndSupportScreen(
//                            navController
//                        )
//                    }
//                    composable(OtherScreen.ThankYou.route) { ThankYouScreen(navController) }
//                    composable(OtherScreen.Appearance.route) {
//                        AppearanceScreen(
//                            navController = navController
//                        )
//                    }
//                    composable(OtherScreen.DeviceInfo.route) {
//                        DeviceInfoScreen(
//                            navController
//                        )
//                    }
//                    composable(OtherScreen.Language.route) { LanguageScreen(navController) }
//
//                    composable(
//                        route = OtherScreen.VideoPlayer.route,
//                        arguments = listOf(navArgument("videoPath") { type = NavType.StringType })
//                    ) { backStackEntry ->
//                        val videoPath = backStackEntry.arguments?.getString("videoPath") ?: ""
//                        VideoPlayerScreen(videoPath = videoPath, navController = navController) {
//                            navController.popBackStack()
//                        }
//
//
//                    }
//
//                    composable(OnboardingScreens.AgeGroup.route) {
//                        OnboardingScreen(
//                            title = "Which age group do you belong to?",
//                            options = listOf("11 and below", "12–20", "20–30", "Above 30"),
//                            onContinueClick = { navController.navigate(OnboardingScreens.Profession.route) },
//                            onSkipClick = { navController.navigate(Screen.Home.route) }
//                        )
//                    }
//
//                    composable(OnboardingScreens.Profession.route) {
//                        OnboardingScreen(
//                            title = "What is your Profession:",
//                            options = listOf("Student", "Parent", "Teacher", "Other"),
//                            onContinueClick = { navController.navigate(OnboardingScreens.Experience.route) },
//                            onSkipClick = { navController.navigate(Screen.Home.route) }
//                        )
//                    }
//
//                    composable(OnboardingScreens.Experience.route) {
//                        OnboardingScreen(
//                            title = "Do you have experience with Kiswahili Sign Language :",
//                            options = listOf(
//                                "I an experience",
//                                "I am conscious of identifying myself",
//                                "I have a little experience",
//                                "I don't have experience"
//                            ),
//                            onContinueClick = { navController.navigate(OnboardingScreens.Goals.route) },
//                            onSkipClick = { navController.navigate(Screen.Home.route) }
//                        )
//                    }
//
//
//
//                    composable(OnboardingScreens.Goals.route) {
//                        OnboardingScreen(
//                            title = "What are your goals of using sign language?",
//                            options = listOf(
//                                "Work",
//                                "Curiosity",
//                                "just for fun",
//                                "Education"
//                            ),
//                            onContinueClick = { navController.navigate(OnboardingScreens.Find.route) },
//                            onSkipClick = { navController.navigate(Screen.Home.route) }
//                        )
//                    }
//
//                    composable(OnboardingScreens.Find.route) {
//                        OnboardingScreen(
//                            title = "How did you find us?",
//                            options = listOf(
//                                "People/Family/Friend",
//                                "Duka La App",
//                                "Google/SocialMedia",
//                                "Other"
//                            ),
//                            onContinueClick = { navController.navigate(OnboardingScreens.FinalScreen.route) },
//                            onSkipClick = { navController.navigate(Screen.Home.route) }
//                        )
//                    }
//                    composable(OnboardingScreens.FinalScreen.route) {
//                        OnboardingScreen(
//                            title = "You are set to start.",
//                            imageResource = R.drawable.party, // Replace with your image
//                            onContinueClick = { navController.navigate(Screen.Home.route) },
//
//                            )
//                    }
//                }
//            }
//        }
//    }
//}
