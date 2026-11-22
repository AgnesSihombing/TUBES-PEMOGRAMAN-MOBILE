package com.kampus.sigap.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.ui.components.*
import com.kampus.sigap.ui.theme.*

@Composable
fun ReportDetailScreen(
    reportId: String,
    onBack: () -> Unit,
    onHistory: () -> Unit,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit
) {
    val report = DummyData.findReport(reportId)

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Detail Laporan", onBack) },
        bottomBar = { SigapBottomBar(BottomTab.Reports, onTabClick, onAddClick) }
    ) { padding ->
        if (report == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Laporan tidak ditemukan", color = SigapTextMuted)
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "#${report.id}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SigapText,
                        modifier = Modifier.weight(1f)
                    )
                    StatusChip(report.status)
                }
                Spacer(Modifier.height(16.dp))
                Text(report.title, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocationOn, null, tint = SigapPrimary)
                    Spacer(Modifier.width(8.dp))
                    Text(report.fullLocation, fontSize = 16.sp, color = SigapText)
                }
                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {
                        Icon(Icons.Outlined.Settings, null, tint = SigapPrimary)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Kategori", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SigapText)
                            Text(report.category, fontSize = 16.sp, color = SigapText)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Prioritas", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SigapText)
                        Spacer(Modifier.height(6.dp))
                        PriorityChip(report.priority)
                    }
                }
                Spacer(Modifier.height(24.dp))
                Text("Deskripsi", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
                Spacer(Modifier.height(8.dp))
                Text(report.description, fontSize = 16.sp, color = SigapText, lineHeight = 24.sp)
                Spacer(Modifier.height(24.dp))
                Text("Foto", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
                Spacer(Modifier.height(12.dp))
                if (report.photos.isEmpty() && report.photoUris.isEmpty()) {
                    Text("Tidak ada foto", color = SigapTextMuted)
                } else {
                    Row(
                        Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        report.photos.forEach { res ->
                            Image(
                                painter = painterResource(res),
                                contentDescription = "Foto kerusakan",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(width = 150.dp, height = 110.dp).clip(RoundedCornerShape(12.dp))
                            )
                        }
                        report.photoUris.forEach { uri ->
                            val bitmap = rememberUriBitmap(uri)
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = "Foto kerusakan",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(width = 150.dp, height = 110.dp).clip(RoundedCornerShape(12.dp))
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(28.dp))
                SigapOutlinedButton("Lihat Riwayat Penanganan", onHistory, Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReportDetailScreenPreview() {
    SigapTheme { ReportDetailScreen(DummyData.reports.first().id, {}, {}, {}, {}) }
}