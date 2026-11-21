package com.example.sigap.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.R
import com.example.sigap.ui.theme.SIGAPTheme

// ---------- Warna ----------
private val Teal = Color(0xFF0F766E)
private val TealLight = Color(0xFF1A9A8D)
private val ScreenBg = Color(0xFFF8FAFC)
private val TextDark = Color(0xFF1E293B)
private val TextGray = Color(0xFF64748B)

/**
 * true  -> file PNG ic_total_report dkk SUDAH ada kotak warna di dalamnya (hasil export dari Figma).
 * false -> PNG cuma ikonnya saja, kotak warna digambar lewat kode.
 */
private const val ICON_ASSET_HAS_BACKGROUND = true

// ---------- Model ----------
enum class ReportStatus(val label: String, val bg: Color, val fg: Color) {
    PENDING("Menunggu", Color(0xFFFEF3C7), Color(0xFFD97706)),
    PROCESSING("Diproses", Color(0xFFE0F2FE), Color(0xFF0284C7)),
    DONE("Selesai", Color(0xFFDCFCE7), Color(0xFF16A34A)),
    REJECTED("Ditolak", Color(0xFFFEE2E2), Color(0xFFDC2626))
}

data class ReportSummary(
    val id: String,
    val title: String,
    val location: String,
    val status: ReportStatus
)

private data class StatData(
    val iconRes: Int,
    val iconBg: Color,
    val count: String,
    val label: String
)

@Composable
fun HomeScreen(
    navController: NavController,
    userName: String = "Martin Simandjuntak",
    onCreateReportClick: () -> Unit = { /* navController.navigate(Screen.CreateReport.route) */ },
    onReportClick: () -> Unit = { /* navController.navigate(Screen.ReportHistory.route) */ },
    onReportDetailClick: (String) -> Unit = { /* navController.navigate("report_detail/$it") */ },
    onNotificationClick: () -> Unit = { /* navController.navigate(Screen.Notification.route) */ },
    onProfileClick: () -> Unit = { /* navController.navigate(Screen.Profile.route) */ }
) {
    val stats = listOf(
        StatData(R.drawable.ic_total_report, Color(0xFFE0F2F1), "3", "Total Laporan"),
        StatData(R.drawable.ic_pending_report, Color(0xFFFEF3C7), "1", "Menunggu"),
        StatData(R.drawable.ic_process_report, Color(0xFFDBEAFE), "1", "Diproses"),
        StatData(R.drawable.ic_completed_report, Color(0xFFDCFCE7), "1", "Selesai"),
        StatData(R.drawable.ic_rejected_report, Color(0xFFFEE2E2), "1", "Ditolak")
    )

    val recentReports = listOf(
        ReportSummary("#LP-20261105-004", "Lampu mati", "Gedung B, Ruang B103", ReportStatus.PROCESSING)
    )

    Scaffold(
        containerColor = ScreenBg,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            HomeBottomBar(
                onReportClick = onReportClick,
                onCreateReportClick = onCreateReportClick,
                onNotificationClick = onNotificationClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Header hijau melengkung
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(Teal, TealLight)),
                            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                        )
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    // Sapaan + foto profil
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Halo,",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$userName 👋",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.img_profile_martin),
                            contentDescription = "Foto Profil",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                                .clickable(onClick = onProfileClick),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        // Kartu statistik
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                stats.chunked(2).forEach { rowItems ->
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        rowItems.forEach { item ->
                                            StatItem(
                                                data = item,
                                                modifier = Modifier.weight(1f),
                                                onClick = onReportClick
                                            )
                                        }
                                        // kalau ganjil (Ditolak sendirian), sisakan ruang kosong
                                        if (rowItems.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Tombol Buat Laporan
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Brush.horizontalGradient(listOf(TealLight, Teal)))
                                .clickable(onClick = onCreateReportClick),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Buat Laporan",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Laporan Terbaru",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        recentReports.forEach { report ->
                            RecentReportCard(
                                report = report,
                                onClick = { onReportDetailClick(report.id) }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    data: StatData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (ICON_ASSET_HAS_BACKGROUND) {
            Image(
                painter = painterResource(id = data.iconRes),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(data.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = data.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = data.count,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(
                text = data.label,
                fontSize = 11.sp,
                color = TextGray
            )
        }
    }
}

@Composable
private fun RecentReportCard(
    report: ReportSummary,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = report.id,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = report.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = report.location,
                    fontSize = 12.sp,
                    color = TextGray
                )
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = report.status.bg
            ) {
                Text(
                    text = report.status.label,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = report.status.fg
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = TextDark,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun HomeBottomBar(
    onReportClick: () -> Unit,
    onCreateReportClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                iconRes = R.drawable.ic_nav_home,
                label = "Beranda",
                isSelected = true,
                onClick = {}
            )
            BottomNavItem(
                iconRes = R.drawable.ic_nav_report,
                label = "Laporan",
                isSelected = false,
                onClick = onReportClick
            )

            // Tombol (+) di tengah
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(TealLight, Teal)))
                        .clickable(onClick = onCreateReportClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Buat Laporan",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            BottomNavItem(
                iconRes = R.drawable.ic_nav_notification,
                label = "Notifikasi",
                isSelected = false,
                onClick = onNotificationClick
            )
            BottomNavItem(
                iconRes = R.drawable.ic_nav_profile,
                label = "Profil",
                isSelected = false,
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) Teal else Color(0xFF94A3B8)
    Column(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = tint,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SIGAPTheme {
        HomeScreen(navController = rememberNavController())
    }
}