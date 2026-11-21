package com.kampus.sigapapp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kampus.sigapapp.ui.screens.auth.EmailVerificationScreen
import com.kampus.sigapapp.ui.screens.auth.ForgotPasswordScreen
import com.kampus.sigapapp.ui.screens.auth.LoginScreen
import com.kampus.sigapapp.ui.screens.auth.NewPasswordScreen
import com.kampus.sigapapp.ui.screens.auth.OnboardingScreen
import com.kampus.sigapapp.ui.screens.auth.RegisterScreen
import com.kampus.sigapapp.ui.screens.auth.SplashScreen
import com.kampus.sigapapp.ui.screens.home.HomeScreen
import com.kampus.sigapapp.ui.screens.notification.NotificationScreen
import com.kampus.sigapapp.ui.screens.profile.AboutScreen
import com.kampus.sigapapp.ui.screens.profile.ChangePasswordScreen
import com.kampus.sigapapp.ui.screens.profile.FaqScreen
import com.kampus.sigapapp.ui.screens.profile.ProfileScreen
import com.kampus.sigapapp.ui.screens.report.CameraScreen
import com.kampus.sigapapp.ui.screens.report.CreateReportScreen
import com.kampus.sigapapp.ui.screens.report.FeedbackScreen
import com.kampus.sigapapp.ui.screens.report.MyReportsScreen
import com.kampus.sigapapp.ui.screens.report.ReportDetailScreen
import com.kampus.sigapapp.ui.screens.report.ReportHistoryScreen
import com.kampus.sigapapp.ui.screens.report.ReportSuccessScreen
import com.kampus.sigapapp.ui.theme.SigapTheme

private const val Phone = "spec:width=411dp,height=891dp"

@Preview(name = "Splash", device = Phone)
@Composable
fun PreviewSplash() = SigapTheme { SplashScreen(onFinished = {}) }

@Preview(name = "Onboarding", device = Phone)
@Composable
fun PreviewOnboarding() = SigapTheme { OnboardingScreen(onFinished = {}) }

@Preview(name = "Login", device = Phone)
@Composable
fun PreviewLogin() = SigapTheme {
    LoginScreen(onLogin = {}, onRegister = {}, onForgotPassword = {})
}

@Preview(name = "Daftar", device = Phone)
@Composable
fun PreviewRegister() = SigapTheme { RegisterScreen(onBack = {}, onRegistered = {}) }

@Preview(name = "Verifikasi Email", device = Phone)
@Composable
fun PreviewEmailVerification() = SigapTheme {
    EmailVerificationScreen(onBack = {}, onOpenEmail = {})
}

@Preview(name = "Lupa Kata Sandi", device = Phone)
@Composable
fun PreviewForgotPassword() = SigapTheme {
    ForgotPasswordScreen(onBack = {}, onOpenEmail = {})
}

@Preview(name = "Atur Kata Sandi Baru", device = Phone)
@Composable
fun PreviewNewPassword() = SigapTheme { NewPasswordScreen(onBack = {}, onSaved = {}) }

@Preview(name = "Beranda", device = Phone)
@Composable
fun PreviewHome() = SigapTheme {
    HomeScreen(onCreateReport = {}, onReportClick = {}, onNavigate = {})
}

@Preview(name = "Laporan Saya", device = Phone)
@Composable
fun PreviewMyReports() = SigapTheme {
    MyReportsScreen(onBack = {}, onReportClick = {}, onNavigate = {})
}

@Preview(name = "Detail Laporan", device = Phone)
@Composable
fun PreviewReportDetail() = SigapTheme {
    ReportDetailScreen(
        reportId = "LP-20261108-001",
        onBack = {},
        onHistory = {},
        onNavigate = {}
    )
}

@Preview(name = "Detail Laporan Selesai", device = Phone)
@Composable
fun PreviewReportDetailDone() = SigapTheme {
    ReportDetailScreen(
        reportId = "LP-20261103-007",
        onBack = {},
        onHistory = {},
        onNavigate = {}
    )
}

@Preview(name = "Riwayat Penanganan - Diproses", device = Phone)
@Composable
fun PreviewReportHistoryProcess() = SigapTheme {
    ReportHistoryScreen(reportId = "LP-20261105-004", onBack = {}, onFeedback = {})
}

@Preview(name = "Riwayat Penanganan - Selesai", device = Phone)
@Composable
fun PreviewReportHistoryDone() = SigapTheme {
    ReportHistoryScreen(reportId = "LP-20261103-007", onBack = {}, onFeedback = {})
}

@Preview(name = "Buat Laporan", device = Phone)
@Composable
fun PreviewCreateReport() = SigapTheme {
    CreateReportScreen(photoTaken = false, onAddPhoto = {}, onBack = {}, onSubmit = {})
}

@Preview(name = "Ambil Foto", device = Phone)
@Composable
fun PreviewCamera() = SigapTheme { CameraScreen(onBack = {}, onPhotoTaken = {}) }

@Preview(name = "Laporan Berhasil", device = Phone)
@Composable
fun PreviewReportSuccess() = SigapTheme {
    ReportSuccessScreen(onViewReport = {}, onBackToHome = {})
}

@Preview(name = "Beri Feedback", device = Phone)
@Composable
fun PreviewFeedback() = SigapTheme { FeedbackScreen(onBack = {}, onSubmit = {}) }

@Preview(name = "Notifikasi", device = Phone)
@Composable
fun PreviewNotification() = SigapTheme {
    NotificationScreen(onBack = {}, onReportClick = {}, onNavigate = {})
}

@Preview(name = "Profil", device = Phone)
@Composable
fun PreviewProfile() = SigapTheme {
    ProfileScreen(
        onBack = {},
        onChangePassword = {},
        onFaq = {},
        onAbout = {},
        onLogout = {},
        onNavigate = {}
    )
}

@Preview(name = "Ubah Kata Sandi", device = Phone)
@Composable
fun PreviewChangePassword() = SigapTheme {
    ChangePasswordScreen(onBack = {}, onSaved = {}, onNavigate = {})
}

@Preview(name = "FAQ", device = Phone)
@Composable
fun PreviewFaq() = SigapTheme { FaqScreen(onBack = {}) }

@Preview(name = "Tentang SIGAP", device = Phone)
@Composable
fun PreviewAbout() = SigapTheme { AboutScreen(onBack = {}) }