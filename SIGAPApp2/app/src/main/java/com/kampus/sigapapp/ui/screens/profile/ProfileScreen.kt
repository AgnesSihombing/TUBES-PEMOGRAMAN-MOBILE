package com.kampus.sigapapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.theme.*

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onChangePassword: () -> Unit,
    onFaq: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var notificationsOn by rememberSaveable { mutableStateOf(true) }

    MainScaffold(selected = Routes.PROFILE, onNavigate = onNavigate) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(onBack = onBack)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.img_profile_photo),
                        contentDescription = "Foto profil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(BrandPrimary)
                            .border(2.dp, AppBackground, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = "Ubah foto",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Martin Simanjuntak",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(Modifier.height(4.dp))
                Text(text = "241402157 - martin@usu.ac.id", fontSize = 15.sp, color = TextGray)
                Spacer(Modifier.height(24.dp))
                MenuRow(
                    icon = Icons.Outlined.Lock,
                    title = "Ubah Kata Sandi",
                    onClick = onChangePassword
                ) { Chevron() }
                MenuRow(
                    icon = Icons.Outlined.Notifications,
                    title = "Pengaturan Notifikasi",
                    onClick = { notificationsOn = !notificationsOn }
                ) {
                    Switch(
                        checked = notificationsOn,
                        onCheckedChange = { notificationsOn = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = BrandPrimary
                        )
                    )
                }
                MenuRow(
                    icon = Icons.Outlined.Language,
                    title = "Bahasa",
                    onClick = {}
                ) {
                    Text(text = "Bahasa Indonesia", fontSize = 14.sp, color = TextDark)
                    Chevron()
                }
                MenuRow(
                    icon = Icons.AutoMirrored.Outlined.HelpOutline,
                    title = "FAQ",
                    onClick = onFaq
                ) { Chevron() }
                MenuRow(
                    icon = Icons.Outlined.Info,
                    title = "Tentang SIGAP",
                    onClick = onAbout
                ) { Chevron() }
                MenuRow(
                    icon = Icons.AutoMirrored.Outlined.Logout,
                    title = "Keluar",
                    tint = DangerRed,
                    onClick = onLogout
                ) {}
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun Chevron() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = null,
        tint = BrandPrimary
    )
}

@Composable
private fun MenuRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    tint: Color = PrimaryDark,
    trailing: @Composable () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(26.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 17.sp,
                color = TextDark
            )
            trailing()
        }
        HorizontalDivider(color = LineColor)
    }
}