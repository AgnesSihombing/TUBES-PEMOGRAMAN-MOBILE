package com.kampus.sigap.ui.screens.home

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.model.Report
import com.kampus.sigap.model.ReportStatus
import com.kampus.sigap.ui.components.BottomTab
import com.kampus.sigap.ui.components.ReportCard
import com.kampus.sigap.ui.components.SigapBottomBar
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.theme.*

private data class StatItem(
    val label: String,
    val count: Int,
    val icon: ImageVector,
    val container: Color,
    val tint: Color
)

@Composable
fun HomeScreen(
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit,
    onReportClick: (String) -> Unit
) {
    val reports = DummyData.reports

    Scaffold(
        containerColor = SigapBackground,
        bottomBar = { SigapBottomBar(BottomTab.Home, onTabClick, onAddClick) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(
                            SigapGradientDiagonal,
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                )
                Column(Modifier.statusBarsPadding().padding(horizontal = 20.dp, vertical = 20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Halo,", color = Color.White, fontSize = 18.sp)
                            Text(
                                "${DummyData.userName} \uD83D\uDC4B",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.img_avatar),
                            contentDescription = "Foto profil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape)
                                .clickable { onTabClick(BottomTab.Profile) }
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    StatsCard(reports)
                }
            }
            Spacer(Modifier.height(24.dp))
            SigapPrimaryButton(
                "+  Buat Laporan",
                onAddClick,
                Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Laporan Terbaru",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SigapText,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Lihat semua",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = SigapPrimary,
                    modifier = Modifier.clickable { onTabClick(BottomTab.Reports) }
                )
            }
            Spacer(Modifier.height(12.dp))
            Column(
                Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                reports.take(2).forEach { report ->
                    ReportCard(report, onClick = { onReportClick(report.id) })
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatsCard(reports: List<Report>) {
    val stats = listOf(
        StatItem("Total Laporan", reports.size, Icons.Outlined.MenuBook, SigapTint, SigapPrimary),
        StatItem(
            "Menunggu",
            reports.count { it.status == ReportStatus.Waiting },
            Icons.Outlined.Schedule, Color(0xFFFFF0C9), Color(0xFFE08A00)
        ),
        StatItem(
            "Diproses",
            reports.count { it.status == ReportStatus.Processed || it.status == ReportStatus.InProgress },
            Icons.Outlined.Build, Color(0xFFDDF0FD), Color(0xFF0B87D6)
        ),
        StatItem(
            "Selesai",
            reports.count { it.status == ReportStatus.Done },
            Icons.Outlined.TaskAlt, SigapTint, SigapPrimary
        ),
        StatItem(
            "Ditolak",
            reports.count { it.status == ReportStatus.Rejected },
            Icons.Outlined.Cancel, Color(0xFFFDE2E3), Color(0xFFE5484D)
        )
    )
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SigapSurface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            stats.chunked(2).forEach { row ->
                Row(Modifier.fillMaxWidth()) {
                    row.forEach { StatTile(it, Modifier.weight(1f)) }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun StatTile(item: StatItem, modifier: Modifier = Modifier) {
    val inspection = LocalInspectionMode.current
    var started by remember { mutableStateOf(inspection) }
    LaunchedEffect(Unit) { started = true }
    val value by animateIntAsState(if (started) item.count else 0, tween(900), label = "count")

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(item.container),
            contentAlignment = Alignment.Center
        ) {
            Icon(item.icon, null, tint = item.tint, modifier = Modifier.size(26.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(value.toString(), fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Text(item.label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SigapText)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    SigapTheme { HomeScreen({}, {}, {}) }
}