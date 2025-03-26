package com.example.kalimani.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String="", val icon: ImageVector?= null) {

    object Home : Screen("home", "Learn", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    //object Home : Screen("home", "Home", Icons.Default.Home)
    object Setting : Screen("setting", "Setting", Icons.Default.Settings)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)


}

sealed class OtherScreen(val route: String) {
    object Splash : OtherScreen("splash")
    object Signin : OtherScreen("signIn")
    object Signup : OtherScreen("signup")
    object VideoPlayer : OtherScreen("video_player/{videoPath}"){
        fun createRoute(videoPath: String) = "video_player/$videoPath"
    }
    object LessonList : OtherScreen("lesson_list/{category}") {
        fun createRoute(category: String) = "lesson_list/$category"
    }
    object TraslateDetail: OtherScreen("translate_detail")
    object ProfileUpdate: OtherScreen("profile_update")
    object ThankYou: OtherScreen("thankyou")
    object HelpAndSupport: OtherScreen("hepAndSupport")
    object AboutApp: OtherScreen("aboutApp")
    object Appearance: OtherScreen("appearance")
    object DeviceInfo: OtherScreen("deviceInfo")
    object Language: OtherScreen("language")
    object ForgetPassword: OtherScreen("forget_password")

}

sealed class OnboardingScreens(val route: String) {
    object AgeGroup : OnboardingScreens("age_group")
    object Profession : OnboardingScreens("profession")
    object Experience : OnboardingScreens("experience")
    object Goals : OnboardingScreens("goals")
    object Find : OnboardingScreens("find")
    object FinalScreen : OnboardingScreens("final")
}
