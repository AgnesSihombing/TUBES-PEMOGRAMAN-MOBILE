package com.example.sigap.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.R
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

// ---------- Warna lokal ----------
private val TextDark = Color(0xFF1F2937)
private val TextGray = Color(0xFF6B7280)
private val TextHint = Color(0xFF9CA3AF)
private val BorderGray = Color(0xFFE5E7EB)
private val ChipBg = Color(0xFFF3F4F6)
private val SoftGreen = Color(0xFFE6F4F1)
private val GradientStart = Color(0xFF26A69A)

// ---------- Model data ----------
private enum class NotifType { STATUS, VERIFIED, RECEIVED, SYSTEM }

private enum class NotifFilter(val label: String) {
    SEMUA("Semua"), LAPORAN("Laporan"), SISTEM("Sistem")
}

private data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val type: NotifType
)

private val sampleNotifications = listOf(
    NotificationItem(
        "Status Laporan Diperbarui",
        "Laporan #LP-20261108-001 kini dalam pengerjaan.",
        "Hari ini, 14.20",
        NotifType.STATUS
    ),
    NotificationItem(
        "Laporan Diverifikasi",
        "Laporan #LP-20261105-001 telah diverifikasi dan diproses.",
        "Hari ini, 10.30",
        NotifType.VERIFIED
    ),
    NotificationItem(
        "Laporan Diterima",
        "Laporan #LP-20261105-004 telah sukses dikirim.",
        "5 Nov 2026, 16.45",
        NotifType.RECEIVED
    ),
    NotificationItem(
        "Pemeliharaan Sistem Terjadwal",
        "Sistem SIGAP akan melakukan pemeliharaan rutin pada 12 Nov 2026, pukul 02.00-04.00 WIB. Layanan pelaporan dan monitoring akan sementara tidak tersedia.",
        "8 Nov 2026, 09.00",
        NotifType.SYSTEM
    )
)

// Semua ikon memakai material-icons-core, jadi tidak perlu dependency tambahan
private fun NotifType.icon(): ImageVector = when (this) {
    NotifType.STATUS -> Icons.Outlined.Build
    NotifType.VERIFIED -> Icons.Outlined.CheckCircle
    NotifType.RECEIVED -> Icons.Outlined.Edit
    NotifType.SYSTEM -> Icons.Outlined.Info
}

// ---------- Screen ----------
@Composable
fun NotificationScreen(navController: NavHostController) {
    var filter by rememberSaveable { mutableStateOf(NotifFilter.SEMUA) }

    val items = when (filter) {
        NotifFilter.SEMUA -> sampleNotifications
        NotifFilter.LAPORAN -> sampleNotifications.filter { it.type != NotifType.SYSTEM }
        NotifFilter.SISTEM -> sampleNotifications.filter { it.type == NotifType.SYSTEM }
    }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            NotificationBottomBar(
                onHomeClick = { navController.navigate("home") },
                onReportClick = { navController.navigate("report_history") },
                onAddClick = { navController.navigate("create_report") },
                onProfileClick = { navController.navigate("profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Notifikasi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Spacer(Modifier.height(12.dp))

            // Filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NotifFilter.values().forEach { item ->
                    FilterChip(
                        label = item.label,
                        selected = item == filter,
                        modifier = Modifier.weight(1f),
                        onClick = { filter = item }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Daftar notifikasi
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item -> NotificationCard(item) }
            }
        }
    }
}

// ---------- Komponen ----------
@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(50)
    Box(
        modifier = modifier
            .height(30.dp)
            .clip(shape)
            .then(
                if (selected) Modifier.background(
                    Brush.horizontalGradient(listOf(GradientStart, SigapPrimary))
                ) else Modifier.background(ChipBg)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else TextDark
        )
    }
}

@Composable
private fun NotificationCard(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SoftGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.type.icon(),
                contentDescription = null,
                tint = SigapPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = item.message,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = TextGray
            )
            Spacer(Modifier.height(6.dp))
            Text(text = item.time, fontSize = 10.sp, color = TextHint)
        }
    }
}

// ---------- Bottom bar ----------
@Composable
private fun NotificationBottomBar(
    onHomeClick: () -> Unit,
    onReportClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = BorderGray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem("Beranda", false, Modifier.weight(1f), onHomeClick) { tint ->
                Icon(Icons.Outlined.Home, null, tint = tint, modifier = Modifier.size(22.dp))
            }
            NavItem("Laporan", false, Modifier.weight(1f), onReportClick) { tint ->
                Icon(painterResource(R.drawable.ic_nav_report), null, tint = tint, modifier = Modifier.size(22.dp))
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(SigapPrimary)
                        .clickable(onClick = onAddClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Buat laporan",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            NavItem("Notifikasi", true, Modifier.weight(1f), {}) { tint ->
                Icon(painterResource(R.drawable.ic_nav_notification), null, tint = tint, modifier = Modifier.size(22.dp))
            }
            NavItem("Profil", false, Modifier.weight(1f), onProfileClick) { tint ->
                Icon(painterResource(R.drawable.ic_nav_profile), null, tint = tint, modifier = Modifier.size(22.dp))
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (tint: Color) -> Unit
) {
    val tint = if (selected) SigapPrimary else TextHint
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon(tint)
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = tint,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview() {
    SIGAPTheme {
        NotificationScreen(navController = rememberNavController())
    }
}
