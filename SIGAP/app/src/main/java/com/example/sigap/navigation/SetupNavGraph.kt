package com.example.sigap.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sigap.ui.screens.auth.EmailVerificationScreen
import com.example.sigap.ui.screens.auth.LoginScreen
import com.example.sigap.ui.screens.auth.OnboardingScreen
import com.example.sigap.ui.screens.auth.OnboardingScreen2
import com.example.sigap.ui.screens.auth.OnboardingScreen3
import com.example.sigap.ui.screens.auth.RegisterScreen
import com.example.sigap.ui.screens.auth.SplashScreen
import com.example.sigap.ui.screens.home.HomeScreen
import com.example.sigap.ui.screens.profile.ProfileScreen
import com.example.sigap.ui.screens.report.CreateReportScreen
import com.example.sigap.ui.screens.report.ReportHistoryScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Onboarding1.route) {
            OnboardingScreen(navController = navController)
        }
        composable(route = Screen.Onboarding2.route) {
            OnboardingScreen2(navController = navController)
        }
        composable(route = Screen.Onboarding3.route) {
            OnboardingScreen3(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginClick = { email, password ->
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Email dan kata sandi wajib diisi", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onForgotPasswordClick = {
                    Toast.makeText(context, "Tautan pemulihan kata sandi dikirim ke email kampus", Toast.LENGTH_SHORT).show()
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.EmailVerification.route) {
            EmailVerificationScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.CreateReport.route) {
            CreateReportScreen(navController = navController)
        }
        composable(route = Screen.ReportHistory.route) {
            ReportHistoryScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}