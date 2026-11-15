package com.sigap.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sigap.mobile.ui.admin.dashboard.DashboardScreen
import com.sigap.mobile.ui.admin.laporan.LaporanListScreen
import com.sigap.mobile.ui.admin.login.AdminLoginScreen
import com.sigap.mobile.ui.admin.dashboard.DashboardScreen
import com.sigap.mobile.ui.theme.SIGAPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContent = titik awal UI Compose
        setContent {
            SIGAPTheme {
                DashboardScreen()
            }
        }
    }
}