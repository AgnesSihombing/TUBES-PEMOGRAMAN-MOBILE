package com.kampus.sigapapp.ui.screens.home

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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.ReportStatus
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.ReportCard
import com.kampus.sigapapp.ui.theme.*

private data class StatUi(
    val value: Int,
    val label: String,
    val icon: ImageVector,
    val background: Color,
    val tint: Color
)

@Composable
fun HomeScreen(
    onCreateReport: () -> Unit,
    onReportClick: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    val reports = DummyData.reports
    val stats = listOf(
        StatUi(reports.size, "Total Laporan", Icons.Outlined.Description, PrimarySoft, PrimaryDark),
        StatUi(
            reports.count { it.status == ReportStatus.WAITING },
            "Menunggu", Icons.Outlined.Schedule, ChipWaitingBg, ChipWaitingText
        ),
        StatUi(
            reports.count { it.status == ReportStatus.PROCESS },
            "Diproses", Icons.Outlined.Build, ChipProcessBg, ChipProcessText
        ),
        StatUi(
            reports.count { it.status == ReportStatus.DONE },
            "Selesai", Icons.Outlined.TaskAlt, ChipDoneBg, ChipDoneText
        ),
        StatUi(
            reports.count { it.status == ReportStatus.REJECTED },
            "Ditolak", Icons.Outlined.Cancel, ChipRejectedBg, ChipRejectedText
        )
    )
    val latest = reports.first { it.status == ReportStatus.PROCESS }
    val cardShape = RoundedCornerShape(16.dp)

    MainScaffold(selected = Routes.HOME, onNavigate = onNavigate) {
        Box(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(GradientPrimary)
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Halo,", color = Color.White, fontSize = 18.sp)
                        Text(
                            text = "Martin Simanjuntak 👋",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.img_profile_photo),
                        contentDescription = "Foto profil",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .clickable { onNavigate(Routes.PROFILE) },
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .clip(cardShape)
                        .background(AppSurface)
                        .border(1.dp, LineColor, cardShape)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    stats.chunked(2).forEach { rowItems ->
                        Row(Modifier.fillMaxWidth()) {
                            rowItems.forEach { StatItem(it, Modifier.weight(1f)) }
                            if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                GradientButton(
                    text = "Buat Laporan",
                    onClick = onCreateReport,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    leadingIcon = Icons.Outlined.Add
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Laporan Terbaru",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(Modifier.height(12.dp))
                ReportCard(
                    report = latest,
                    onClick = { onReportClick(latest.id) },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun StatItem(stat: StatUi, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(stat.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = null,
                tint = stat.tint,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = "${stat.value}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(text = stat.label, fontSize = 14.sp, color = TextDark)
        }
    }
}