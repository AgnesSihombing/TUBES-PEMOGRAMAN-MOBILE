package com.sigap.mobile.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.fillMaxSize
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.ui.admin.dashboard.DashboardScreen
import com.sigap.mobile.ui.admin.laporan.DetailLaporanScreen
import com.sigap.mobile.ui.admin.laporan.LaporanListScreen
import com.sigap.mobile.ui.admin.laporan.PerbaruiStatusScreen
import com.sigap.mobile.ui.admin.laporan.TugaskanTeknisiScreen
import com.sigap.mobile.ui.admin.laporan.dummyLaporan
import com.sigap.mobile.ui.admin.login.AdminLoginScreen
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.admin.pengguna.DetailPenggunaScreen
import com.sigap.mobile.ui.admin.pengguna.PenggunaListScreen
import com.sigap.mobile.ui.admin.pengguna.TambahPenggunaScreen
import com.sigap.mobile.ui.admin.pengguna.dummyPengguna
import com.sigap.mobile.ui.admin.profil.ProfilScreen
import com.sigap.mobile.ui.admin.profil.PusatBantuanScreen
import com.sigap.mobile.ui.admin.profil.TentangScreen
import com.sigap.mobile.ui.admin.profil.UbahProfilScreen
import com.sigap.mobile.ui.admin.profil.UbahSandiScreen
import com.sigap.mobile.ui.admin.profil.NotifikasiScreen

object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val LAPORAN = "laporan"
    const val DETAIL = "laporan/{id}"
    const val PENGGUNA = "pengguna"
    const val PROFIL = "profil"
    const val TUGASKAN = "laporan/{id}/tugaskan"
    const val STATUS = "laporan/{id}/status"
    const val TAMBAH_PENGGUNA = "pengguna/tambah"
    const val DETAIL_PENGGUNA = "pengguna/detail/{id}"
    const val PROFIL_UBAH = "profil/ubah"
    const val PROFIL_SANDI = "profil/sandi"
    const val PROFIL_NOTIF = "profil/notifikasi"
    const val PROFIL_BANTUAN = "profil/bantuan"
    const val PROFIL_TENTANG = "profil/tentang"

    // ID mengandung '#', jadi harus di-encode sebelum masuk ke alamat
    fun detail(id: String) = "laporan/${Uri.encode(id)}"
    fun tugaskan(id: String) = "laporan/${Uri.encode(id)}/tugaskan"
    fun status(id: String) = "laporan/${Uri.encode(id)}/status"
    fun detailPengguna(id: String) = "pengguna/detail/${Uri.encode(id)}"
}

private fun AdminTab.route() = when (this) {
    AdminTab.Beranda -> Routes.DASHBOARD
    AdminTab.Laporan -> Routes.LAPORAN
    AdminTab.Pengguna -> Routes.PENGGUNA
    AdminTab.Profil -> Routes.PROFIL
}

// Pindah tab tanpa menumpuk layar di back stack
private fun NavHostController.navigateTab(tab: AdminTab) {
    navigate(tab.route()) {
        popUpTo(Routes.DASHBOARD) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

// NavHost = tempat semua layar didaftarkan; startDestination = layar pertama
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            AdminLoginScreen(
                onLoginClick = { _, _ ->
                    // Login dummy: langsung ke dashboard, layar login dibuang dari back stack
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(onTabSelected = { navController.navigateTab(it) })
        }

        composable(Routes.LAPORAN) {
            LaporanListScreen(
                onTabSelected = { navController.navigateTab(it) },
                onLaporanClick = {
                    if (it.status == LaporanStatus.Diproses) {
                        navController.navigate(Routes.status(it.id))
                    } else {
                        navController.navigate(Routes.detail(it.id))
                    }
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            // Cari laporan berdasarkan ID.
            val laporan = dummyLaporan.firstOrNull { it.id == id } ?: dummyLaporan.first()
            DetailLaporanScreen(
                laporan = laporan,
                onBack = { navController.popBackStack() },
                onVerifikasi = { navController.navigate(Routes.tugaskan(laporan.id)) },
                onTabSelected = { navController.navigateTab(it) }
            )
        }

        composable(
            route = Routes.TUGASKAN,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            val laporan = dummyLaporan.firstOrNull { it.id == id } ?: dummyLaporan.first()
            TugaskanTeknisiScreen(
                laporan = laporan,
                onBack = { navController.popBackStack() },
                onTugaskan = { _, _ ->
                    // Nanti: simpan ke Firebase. Sekarang langsung kembali ke daftar laporan.
                    navController.popBackStack(Routes.LAPORAN, inclusive = false)
                },
                onTabSelected = { navController.navigateTab(it) }
            )
        }

        composable(
            route = Routes.STATUS,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            val laporan = dummyLaporan.firstOrNull { it.id == id } ?: dummyLaporan.first()
            PerbaruiStatusScreen(
                statusAwal = laporan.status,
                onBack = { navController.popBackStack() },
                onSimpan = { _, _, _ ->
                    // Nanti: simpan ke Firebase. Sekarang kembali ke daftar laporan.
                    navController.popBackStack()
                },
                onTabSelected = { navController.navigateTab(it) }
            )
        }

        composable(Routes.PENGGUNA) {
            PenggunaListScreen(
                onTabSelected = { navController.navigateTab(it) },
                onTambahClick = { navController.navigate(Routes.TAMBAH_PENGGUNA) },
                onPenggunaClick = { navController.navigate(Routes.detailPengguna(it.id)) }
            )
        }

        composable(Routes.TAMBAH_PENGGUNA) {
            TambahPenggunaScreen(
                onBack = { navController.popBackStack() },
                // Nanti: simpan ke Firebase. Sekarang kembali ke daftar.
                onSimpan = { _, _, _, _, _ -> navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }

        composable(
            route = Routes.DETAIL_PENGGUNA,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            val pengguna = dummyPengguna.firstOrNull { it.id == id } ?: dummyPengguna.first()
            DetailPenggunaScreen(
                pengguna = pengguna,
                onBack = { navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }

        composable(Routes.PROFIL) {
            ProfilScreen(
                onUbahProfil = { navController.navigate(Routes.PROFIL_UBAH) },
                onUbahSandi = { navController.navigate(Routes.PROFIL_SANDI) },
                onNotifikasi = { navController.navigate(Routes.PROFIL_NOTIF) },
                onBantuan = { navController.navigate(Routes.PROFIL_BANTUAN) },
                onTentang = { navController.navigate(Routes.PROFIL_TENTANG) },
                onTabSelected = { navController.navigateTab(it) },
                onKeluar = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PROFIL_UBAH) {
            UbahProfilScreen(
                onBack = { navController.popBackStack() },
                onSimpan = { navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }
        composable(Routes.PROFIL_SANDI) {
            UbahSandiScreen(
                onBack = { navController.popBackStack() },
                onSimpan = { _, _ -> navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }
        composable(Routes.PROFIL_NOTIF) {
            NotifikasiScreen(
                onBack = { navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }
        composable(Routes.PROFIL_BANTUAN) {
            PusatBantuanScreen(
                onBack = { navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }
        composable(Routes.PROFIL_TENTANG) {
            TentangScreen(
                onBack = { navController.popBackStack() },
                onTabSelected = { navController.navigateTab(it) }
            )
        }
    }
}


