package com.sigap.mobile.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding1 : Screen("onboarding_1")
    object Onboarding2 : Screen("onboarding_2")
    object Onboarding3 : Screen("onboarding_3")
    object Login : Screen("login")
    object Register : Screen("register")
    object EmailVerification : Screen("email_verification")
    object Home : Screen("home")
    object CreateReport : Screen("create_report")
    object ReportHistory : Screen("report_history")
    object Profile : Screen("profile")
}