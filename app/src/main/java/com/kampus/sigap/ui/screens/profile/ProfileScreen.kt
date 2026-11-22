package com.kampus.sigap.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.ui.components.BottomTab
import com.kampus.sigap.ui.components.SigapBottomBar
import com.kampus.sigap.ui.theme.*

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onChangePassword: () -> Unit,
    onFaq: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit
) {
    var notificationEnabled by rememberSaveable { mutableStateOf(true) }
    var language by rememberSaveable { mutableStateOf("Bahasa Indonesia") }
    var showLanguage by remember { mutableStateOf(false) }
    var showLogout by remember { mutableStateOf(false) }

    if (showLanguage) {
        AlertDialog(
            onDismissRequest = { showLanguage = false },
            title = { Text("Pilih Bahasa", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    listOf("Bahasa Indonesia", "English").forEach { option ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable { language = option; showLanguage = false },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = language == option,
                                onClick = { language = option; showLanguage = false },
                                colors = RadioButtonDefaults.colors(selectedColor = SigapPrimary)
                            )
                            Text(option, fontSize = 16.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguage = false }) {
                    Text("Tutup", color = SigapPrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showLogout) {
        AlertDialog(
            onDismissRequest = { showLogout = false },
            title = { Text("Keluar dari akun?", fontWeight = FontWeight.Bold) },
            text = { Text("Kamu perlu masuk lagi untuk menggunakan SIGAP.") },
            confirmButton = {
                TextButton(onClick = { showLogout = false; onLogout() }) {
                    Text("Keluar", color = SigapError, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogout = false }) {
                    Text("Batal", color = SigapText)
                }
            }
        )
    }

    Scaffold(
        containerColor = SigapBackground,
        bottomBar = { SigapBottomBar(BottomTab.Profile, onTabClick, onAddClick) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Box {
                Image(
                    painter = painterResource(R.drawable.img_avatar),
                    contentDescription = "Foto profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(116.dp).clip(CircleShape)
                )
                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(SigapPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.CameraAlt, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(DummyData.userName, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(4.dp))
            Text("${DummyData.userId} - ${DummyData.userEmail}", fontSize = 14.sp, color = SigapTextMuted)
            Spacer(Modifier.height(24.dp))

            ProfileMenuItem(Icons.Outlined.Lock, "Ubah Kata Sandi", onChangePassword)
            ProfileMenuItem(
                Icons.Outlined.Notifications,
                "Pengaturan Notifikasi",
                { notificationEnabled = !notificationEnabled }
            ) {
                Switch(
                    checked = notificationEnabled,
                    onCheckedChange = { notificationEnabled = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = SigapPrimary, checkedThumbColor = Color.White)
                )
            }
            ProfileMenuItem(Icons.Outlined.Language, "Bahasa", { showLanguage = true }) {
                Text(language, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SigapPrimaryDark)
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = SigapPrimary)
            }
            ProfileMenuItem(Icons.Outlined.HelpOutline, "FAQ", onFaq)
            ProfileMenuItem(Icons.Outlined.Info, "Tentang SIGAP", onAbout)
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Outlined.Logout,
                title = "Keluar",
                onClick = { showLogout = true },
                iconTint = SigapError,
                titleColor = SigapError,
                trailing = {}
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    iconTint: Color = SigapPrimaryDark,
    titleColor: Color = SigapText,
    trailing: @Composable RowScope.() -> Unit = {
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = SigapPrimary)
    }
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = iconTint, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(16.dp))
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Medium, color = titleColor, modifier = Modifier.weight(1f))
            trailing()
        }
        HorizontalDivider(color = SigapBorder)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    SigapTheme { ProfileScreen({}, {}, {}, {}, {}, {}, {}) }
}