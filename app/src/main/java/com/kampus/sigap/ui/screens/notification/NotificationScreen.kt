package com.kampus.sigap.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.BottomTab
import com.kampus.sigap.ui.components.SigapBottomBar
import com.kampus.sigap.ui.components.SigapFilterChip
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

private enum class NotificationType { Report, System }

private enum class NotificationFilter(val label: String, val type: NotificationType?) {
    All("Semua", null),
    Report("Laporan", NotificationType.Report),
    System("Sistem", NotificationType.System)
}

private data class NotificationItem(
    val type: NotificationType,
    val title: String,
    val message: String,
    val time: String,
    val reportId: String? = null
)

private val notifications = listOf(
    NotificationItem(
        NotificationType.Report,
        "Status Laporan Diperbarui",
        "Laporan #LP-20261108-001 kini dalam pengerjaan.",
        "Hari ini, 14.20",
        "LP-20261108-001"
    ),
    NotificationItem(
        NotificationType.Report,
        "Laporan Diverifikasi",
        "Laporan #LP-20261108-001 telah diverifikasi dan diproses.",
        "Hari ini, 10.30",
        "LP-20261108-001"
    ),
    NotificationItem(
        NotificationType.Report,
        "Laporan Diterima",
        "Laporan #LP-20261105-004 telah selesai ditangani.",
        "5 Nov 2026, 16.45",
        "LP-20261105-004"
    ),
    NotificationItem(
        NotificationType.System,
        "Pemeliharaan Sistem Terjadwal",
        "Sistem SIGAP akan melakukan pemeliharaan rutin pada 12 Nov 2026, pukul 02.00–04.00 WIB. Layanan pelaporan dan monitoring akan sementara tidak tersedia.",
        "8 Nov 2026, 09.00"
    )
)

@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    onReportClick: (String) -> Unit,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit
) {
    var filter by rememberSaveable { mutableStateOf(NotificationFilter.All) }
    val items = notifications.filter { filter.type == null || it.type == filter.type }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Notifikasi", onBack) },
        bottomBar = { SigapBottomBar(BottomTab.Notifications, onTabClick, onAddClick) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NotificationFilter.entries.forEach { item ->
                    SigapFilterChip(item.label, filter == item, { filter = item })
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items, key = { it.title + it.time }) { item ->
                    NotificationCard(item, onReportClick)
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(item: NotificationItem, onReportClick: (String) -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    val icon: ImageVector = if (item.type == NotificationType.Report) Icons.Outlined.Description else Icons.Outlined.Info
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(enabled = item.reportId != null) { item.reportId?.let(onReportClick) },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = SigapSurface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(16.dp)) {
            Box(
                Modifier.size(52.dp).clip(RoundedCornerShape(12.dp)).background(SigapTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = SigapPrimary, modifier = Modifier.size(28.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(item.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = SigapText)
                Spacer(Modifier.height(2.dp))
                Text(item.message, fontSize = 15.sp, color = SigapTextMuted, lineHeight = 22.sp)
                Spacer(Modifier.height(6.dp))
                Text(item.time, fontSize = 13.sp, color = SigapTextMuted)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotificationScreenPreview() {
    SigapTheme { NotificationScreen({}, {}, {}, {}) }
}