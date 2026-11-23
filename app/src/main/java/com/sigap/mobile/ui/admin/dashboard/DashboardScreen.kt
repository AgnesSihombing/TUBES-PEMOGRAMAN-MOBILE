package com.sigap.mobile.ui.admin.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.theme.*

// Model data sederhana untuk satu status laporan
private data class StatusItem(val label: String, val count: Int, val color: Color)

@Composable
fun DashboardScreen(
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // Data dummy. Nanti diganti data dari Firebase lewat ViewModel.
    val statuses = listOf(
        StatusItem("Menunggu Verifikasi", 2, StatusMenungguFg),
        StatusItem("Diproses", 4, StatusDiprosesFg),
        StatusItem("Selesai", 3, StatusSelesaiFg),
        StatusItem("Ditolak", 1, StatusDitolakFg)
    )
    val trend = listOf(
        "SEN" to 2, "SEL" to 3, "RAB" to 5, "KAM" to 5,
        "JUM" to 1, "SAB" to 0, "MIN" to 0
    )

    // Scaffold = kerangka layar: konten di tengah, bottom bar di bawah
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Beranda, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // layar bisa di-scroll
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Halo, Admin 👋", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            TotalCard(total = 128)

            // IntrinsicSize.Min membuat keempat kartu sama tinggi
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(24, "Menunggu Verifikasi", StatusMenungguBg, StatusMenungguFg, Modifier.weight(1f).fillMaxHeight())
                StatCard(36, "Diproses", StatusDiprosesBg, StatusDiprosesFg, Modifier.weight(1f).fillMaxHeight())
                StatCard(46, "Selesai", StatusSelesaiBg, StatusSelesaiFg, Modifier.weight(1f).fillMaxHeight())
                StatCard(7, "Ditolak", StatusDitolakBg, StatusDitolakFg, Modifier.weight(1f).fillMaxHeight())
            }

            SigapCard {
                Text("Tren Laporan (7 Hari Terakhir)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(Modifier.height(16.dp))
                TrendChart(trend)
            }

            Text("Statistik Laporan", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            SigapCard {
                PeriodDropdown("7 Hari Terakhir")
                Spacer(Modifier.height(16.dp))
                DonutChart(statuses)
                Spacer(Modifier.height(16.dp))
                StatusLegend(statuses)
            }
        }
    }
}

// Wadah putih bergaris tepi, dipakai berulang di layar ini
@Composable
private fun SigapCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun TotalCard(total: Int) {
    SigapCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Description, null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(total.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Total Laporan", fontSize = 12.sp, color = SigapTextSecondary)
            }
        }
    }
}

@Composable
private fun StatCard(count: Int, label: String, bg: Color, fg: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(36.dp).background(bg, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(count.toString(), color = fg, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(Modifier.height(6.dp))
        Text(label, fontSize = 10.sp, lineHeight = 12.sp, textAlign = TextAlign.Center, color = SigapTextSecondary)
    }
}

// Grafik batang: tinggi tiap batang dihitung dari nilai terhadap nilai tertinggi
@Composable
private fun TrendChart(data: List<Pair<String, Int>>) {
    val max = (data.maxOf { it.second }).coerceAtLeast(1)
    Row(
        modifier = Modifier.fillMaxWidth().height(130.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { (day, value) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val barHeight = maxOf(2f, value.toFloat() / max * 100f)
                Box(
                    modifier = Modifier
                        .width(14.dp)
                        .height(barHeight.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                        )
                )
                Spacer(Modifier.height(6.dp))
                Text(day, fontSize = 9.sp, color = SigapTextSecondary)
            }
        }
    }
}

@Composable
private fun PeriodDropdown(selected: String) {
    // Tampilan saja. Fungsi memilih periode ditambahkan nanti.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(selected, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
    }
}

// Donut chart digambar dengan Canvas: tiap status = satu busur (arc)
// yang sudutnya sebanding dengan jumlahnya dibanding total.
@Composable
private fun DonutChart(items: List<StatusItem>) {
    val total = items.sumOf { it.count }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val strokeWidth = 28.dp.toPx()
            var startAngle = -90f // mulai dari atas
            items.forEach { item ->
                val sweep = item.count.toFloat() / total * 360f
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(size.width - strokeWidth, size.height - strokeWidth),
                    style = Stroke(width = strokeWidth)
                )
                startAngle += sweep
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(total.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Laporan", fontSize = 13.sp, color = SigapTextSecondary)
        }
    }
}

@Composable
private fun StatusLegend(items: List<StatusItem>) {
    val total = items.sumOf { it.count }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(10.dp).background(item.color, CircleShape))
                Spacer(Modifier.width(8.dp))
                Text(item.label, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                Text("${item.count} (${item.count * 100 / total}%)", fontSize = 12.sp, color = SigapTextSecondary)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun DashboardPreview() {
    SIGAPTheme { DashboardScreen() }
}