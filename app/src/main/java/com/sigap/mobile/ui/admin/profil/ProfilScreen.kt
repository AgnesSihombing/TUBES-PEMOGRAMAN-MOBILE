package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.SigapCard
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary
import com.sigap.mobile.ui.theme.StatusDitolakFg

@Composable
fun ProfilScreen(
    nama: String = "Rina Wulandari",   // dummy, nanti dari Firebase Auth
    email: String = "admin@kampus.ac.id",
    onUbahProfil: () -> Unit = {},
    onUbahSandi: () -> Unit = {},
    onNotifikasi: () -> Unit = {},
    onBantuan: () -> Unit = {},
    onTentang: () -> Unit = {},
    onKeluar: () -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // State dialog konfirmasi keluar
    var tampilDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Profil, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text("Profil Admin", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            // Kartu profil
            SigapCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Person, null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(nama, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(email, fontSize = 12.sp, color = SigapTextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Pengaturan Akun", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            MenuCard {
                MenuRow(Icons.Outlined.Person, "Ubah Profil", onUbahProfil)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                MenuRow(Icons.Outlined.Lock, "Ubah Kata Sandi", onUbahSandi)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                MenuRow(Icons.Outlined.Notifications, "Pengaturan Notifikasi", onNotifikasi)
            }

            Spacer(Modifier.height(20.dp))
            Text("Bantuan & Informasi", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            MenuCard {
                MenuRow(Icons.Outlined.HelpOutline, "Pusat Bantuan", onBantuan)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                MenuRow(Icons.Outlined.Info, "Tentang Aplikasi", onTentang)
            }

            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = { tampilDialog = true },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, StatusDitolakFg),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusDitolakFg)
            ) {
                Icon(Icons.AutoMirrored.Outlined.Logout, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Keluar", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "SIGAP • Versi 1.0.0",
                fontSize = 11.sp,
                color = SigapTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Dialog konfirmasi: logout hanya terjadi setelah admin menekan "Keluar"
    if (tampilDialog) {
        AlertDialog(
            onDismissRequest = { tampilDialog = false },
            title = { Text("Keluar dari akun?") },
            text = { Text("Kamu harus login lagi untuk mengakses aplikasi.") },
            confirmButton = {
                TextButton(onClick = {
                    tampilDialog = false
                    onKeluar()
                }) { Text("Keluar", color = StatusDitolakFg) }
            },
            dismissButton = {
                TextButton(onClick = { tampilDialog = false }) { Text("Batal") }
            }
        )
    }
}

// Wadah putih bergaris tepi untuk sekelompok baris menu (tanpa padding agar baris penuh lebar)
@Composable
private fun MenuCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),
        content = content
    )
}

@Composable
private fun MenuRow(icon: ImageVector, teks: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text(teks, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Outlined.ChevronRight, null, tint = SigapTextSecondary)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun ProfilPreview() {
    SIGAPTheme { ProfilScreen() }
}