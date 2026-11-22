package com.kampus.sigap.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kampus.sigap.ui.components.BottomTab
import com.kampus.sigap.ui.screens.auth.ForgotPasswordScreen
import com.kampus.sigap.ui.screens.auth.LoginScreen
import com.kampus.sigap.ui.screens.auth.NewPasswordScreen
import com.kampus.sigap.ui.screens.auth.RegisterScreen
import com.kampus.sigap.ui.screens.home.HomeScreen
import com.kampus.sigap.ui.screens.notification.NotificationScreen
import com.kampus.sigap.ui.screens.onboarding.OnboardingScreen
import com.kampus.sigap.ui.screens.profile.AboutScreen
import com.kampus.sigap.ui.screens.profile.FaqScreen
import com.kampus.sigap.ui.screens.profile.PrivacyScreen
import com.kampus.sigap.ui.screens.profile.ProfileScreen
import com.kampus.sigap.ui.screens.profile.TermsScreen
import com.kampus.sigap.ui.screens.report.CreateReportScreen
import com.kampus.sigap.ui.screens.report.FeedbackScreen
import com.kampus.sigap.ui.screens.report.MyReportsScreen
import com.kampus.sigap.ui.screens.report.ReportDetailScreen
import com.kampus.sigap.ui.screens.report.ReportHistoryScreen
import com.kampus.sigap.ui.screens.report.ReportSuccessScreen
import com.kampus.sigap.ui.screens.splash.SplashScreen
import com.kampus.sigap.ui.theme.SigapTheme

private fun NavHostController.navigateTab(route: String) {
    navigate(route) {
        popUpTo(Routes.Home) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun SigapNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Splash
) {
    val onTabClick: (BottomTab) -> Unit = { navController.navigateTab(it.route) }
    val onAddClick: () -> Unit = {
        navController.navigate(Routes.CreateReport) { launchSingleTop = true }
    }
    val idArgument = listOf(navArgument(Routes.ARG_REPORT_ID) { type = NavType.StringType })

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 12 } },
        exitTransition = { fadeOut(tween(200)) },
        popEnterTransition = { fadeIn(tween(300)) },
        popExitTransition = { fadeOut(tween(200)) + slideOutHorizontally(tween(300)) { it / 12 } }
    ) {
        composable(Routes.Splash) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.Onboarding) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Onboarding) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Login) {
            LoginScreen(
                onLogin = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onForgotPassword = { navController.navigate(Routes.ForgotPassword) },
                onRegister = { navController.navigate(Routes.Register) }
            )
        }
        composable(Routes.Register) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegistered = { navController.popBackStack() }
            )
        }
        composable(Routes.ForgotPassword) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onOpenEmail = { navController.navigate(Routes.NewPassword) }
            )
        }
        composable(Routes.NewPassword) {
            NewPasswordScreen(
                onBack = { navController.popBackStack() },
                onSaved = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ChangePassword) {
            NewPasswordScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
                title = "Ubah Kata Sandi",
                subtitle = "Masukkan kata sandi baru untuk akunmu."
            )
        }
        composable(Routes.Home) {
            HomeScreen(
                onTabClick = onTabClick,
                onAddClick = onAddClick,
                onReportClick = { navController.navigate(Routes.reportDetail(it)) }
            )
        }
        composable(Routes.MyReports) {
            MyReportsScreen(
                onBack = { navController.popBackStack() },
                onReportClick = { navController.navigate(Routes.reportDetail(it)) },
                onTabClick = onTabClick,
                onAddClick = onAddClick
            )
        }
        composable(Routes.ReportDetail, arguments = idArgument) { entry ->
            val id = entry.arguments?.getString(Routes.ARG_REPORT_ID).orEmpty()
            ReportDetailScreen(
                reportId = id,
                onBack = { navController.popBackStack() },
                onHistory = { navController.navigate(Routes.reportHistory(id)) },
                onTabClick = onTabClick,
                onAddClick = onAddClick
            )
        }
        composable(Routes.ReportHistory, arguments = idArgument) { entry ->
            val id = entry.arguments?.getString(Routes.ARG_REPORT_ID).orEmpty()
            ReportHistoryScreen(
                reportId = id,
                onBack = { navController.popBackStack() },
                onFeedback = { navController.navigate(Routes.feedback(id)) }
            )
        }
        composable(Routes.Feedback, arguments = idArgument) {
            FeedbackScreen(
                onBack = { navController.popBackStack() },
                onSubmitted = {
                    navController.navigate(Routes.MyReports) {
                        popUpTo(Routes.Home)
                    }
                }
            )
        }
        composable(Routes.CreateReport) {
            CreateReportScreen(
                onBack = { navController.popBackStack() },
                onSubmitted = { number ->
                    navController.navigate(Routes.reportSuccess(number)) {
                        popUpTo(Routes.CreateReport) { inclusive = true }
                    }
                }
            )
        }
        composable(
            Routes.ReportSuccess,
            arguments = listOf(navArgument(Routes.ARG_REPORT_NUMBER) { type = NavType.StringType })
        ) { entry ->
            val number = entry.arguments?.getString(Routes.ARG_REPORT_NUMBER).orEmpty()
            ReportSuccessScreen(
                reportNumber = number,
                onViewReports = {
                    navController.navigate(Routes.MyReports) { popUpTo(Routes.Home) }
                },
                onBackHome = { navController.popBackStack(Routes.Home, inclusive = false) }
            )
        }
        composable(Routes.Notifications) {
            NotificationScreen(
                onBack = { navController.popBackStack() },
                onReportClick = { navController.navigate(Routes.reportDetail(it)) },
                onTabClick = onTabClick,
                onAddClick = onAddClick
            )
        }
        composable(Routes.Profile) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onChangePassword = { navController.navigate(Routes.ChangePassword) },
                onFaq = { navController.navigate(Routes.Faq) },
                onAbout = { navController.navigate(Routes.About) },
                onLogout = {
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onTabClick = onTabClick,
                onAddClick = onAddClick
            )
        }
        composable(Routes.Faq) { FaqScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.About) {
            AboutScreen(
                onBack = { navController.popBackStack() },
                onTerms = { navController.navigate(Routes.Terms) },
                onPrivacy = { navController.navigate(Routes.Privacy) }
            )
        }
        composable(Routes.Terms) { TermsScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.Privacy) { PrivacyScreen(onBack = { navController.popBackStack() }) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SigapNavHostPreview() {
    SigapTheme { SigapNavHost(startDestination = Routes.Login) }
}