package com.kampus.sigap.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.model.ReportStatus
import com.kampus.sigap.ui.components.*
import com.kampus.sigap.ui.theme.*

private enum class ReportFilter(val label: String, val statuses: Set<ReportStatus>?) {
    All("Semua", null),
    Waiting("Menunggu", setOf(ReportStatus.Waiting)),
    Processed("Diproses", setOf(ReportStatus.Processed, ReportStatus.InProgress)),
    Done("Selesai", setOf(ReportStatus.Done)),
    Rejected("Ditolak", setOf(ReportStatus.Rejected))
}

@Composable
fun MyReportsScreen(
    onBack: () -> Unit,
    onReportClick: (String) -> Unit,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit
) {
    var filter by rememberSaveable { mutableStateOf(ReportFilter.All) }
    val reports = DummyData.reports.filter { filter.statuses == null || it.status in filter.statuses!! }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Laporan Saya", onBack) },
        bottomBar = { SigapBottomBar(BottomTab.Reports, onTabClick, onAddClick) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ReportFilter.entries.forEach { item ->
                    SigapFilterChip(item.label, filter == item, { filter = item })
                }
            }
            if (reports.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada laporan", color = SigapTextMuted)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reports, key = { it.id }) { report ->
                        ReportCard(report, onClick = { onReportClick(report.id) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyReportsScreenPreview() {
    SigapTheme { MyReportsScreen({}, {}, {}, {}) }
}