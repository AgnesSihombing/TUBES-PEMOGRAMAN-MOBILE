package com.kampus.sigap.navigation

object Routes {
    const val ARG_REPORT_ID = "reportId"
    const val ARG_REPORT_NUMBER = "reportNumber"

    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val Login = "login"
    const val Register = "register"
    const val EmailVerification = "email_verification"
    const val ForgotPassword = "forgot_password"
    const val NewPassword = "new_password"
    const val ChangePassword = "change_password"
    const val Home = "home"
    const val MyReports = "my_reports"
    const val ReportDetail = "report_detail/{$ARG_REPORT_ID}"
    const val ReportHistory = "report_history/{$ARG_REPORT_ID}"
    const val Feedback = "feedback/{$ARG_REPORT_ID}"
    const val CreateReport = "create_report"
    const val ReportSuccess = "report_success/{$ARG_REPORT_NUMBER}"
    const val Notifications = "notifications"
    const val Profile = "profile"
    const val Faq = "faq"
    const val About = "about"
    const val Terms = "terms"
    const val Privacy = "privacy"

    fun reportDetail(id: String) = "report_detail/$id"
    fun reportHistory(id: String) = "report_history/$id"
    fun feedback(id: String) = "feedback/$id"
    fun reportSuccess(number: String) = "report_success/$number"
}