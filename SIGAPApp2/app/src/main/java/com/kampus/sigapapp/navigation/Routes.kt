package com.kampus.sigapapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kampus.sigapapp.ui.screens.*

object Routes {
    const val SPLASH = "splash"; const val ONBOARDING = "onboarding"
    const val LOGIN = "login"; const val REGISTER = "register"
    const val VERIFY = "verify"; const val FORGOT = "forgot"
    const val HOME = "home"; const val REPORTS = "reports"
    const val NOTIF = "notif"; const val PROFILE = "profile"
    const val CREATE = "create"; const val SUCCESS = "success/{id}"
    const val DETAIL = "detail/{id}"; const val TIMELINE = "timeline/{id}"
    const val FEEDBACK = "feedback"; const val CHANGEPASS = "changepass"
    const val FAQ = "faq"; const val ABOUT = "about"
    fun success(id: String) = "success/$id"
    fun detail(id: String) = "detail/$id"
    fun timeline(id: String) = "timeline/$id"
}

fun NavController.goMain(route: String) = navigate(route) {
    popUpTo(Routes.HOME) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val idArg = listOf(navArgument("id") { type = NavType.StringType })
    NavHost(nav, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) { SplashScreen { nav.navigate(Routes.ONBOARDING) { popUpTo(Routes.SPLASH) { inclusive = true } } } }
        composable(Routes.ONBOARDING) { OnboardingScreen { nav.navigate(Routes.LOGIN) { popUpTo(Routes.ONBOARDING) { inclusive = true } } } }
        composable(Routes.LOGIN) { LoginScreen(nav) }
        composable(Routes.REGISTER) { RegisterScreen(nav) }
        composable(Routes.VERIFY) { VerifyEmailScreen(nav) }
        composable(Routes.FORGOT) { ForgotPasswordScreen(nav) }
        composable(Routes.HOME) { HomeScreen(nav) }
        composable(Routes.REPORTS) { ReportListScreen(nav) }
        composable(Routes.NOTIF) { NotificationScreen(nav) }
        composable(Routes.PROFILE) { ProfileScreen(nav) }
        composable(Routes.CREATE) { CreateReportScreen(nav) }
        composable(Routes.SUCCESS, idArg) { SuccessScreen(nav, it.arguments?.getString("id").orEmpty()) }
        composable(Routes.DETAIL, idArg) { DetailScreen(nav, it.arguments?.getString("id").orEmpty()) }
        composable(Routes.TIMELINE, idArg) { TimelineScreen(nav, it.arguments?.getString("id").orEmpty()) }
        composable(Routes.FEEDBACK) { FeedbackScreen(nav) }
        composable(Routes.CHANGEPASS) { ChangePasswordScreen(nav) }
        composable(Routes.FAQ) { FaqScreen(nav) }
        composable(Routes.ABOUT) { AboutScreen(nav) }
    }
}
