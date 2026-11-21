package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.ReportStatus
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.FilterChips
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.ReportCard
import com.kampus.sigapapp.ui.components.ScreenHeader

@Composable
fun MyReportsScreen(
    onBack: () -> Unit,
    onReportClick: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    val filters = listOf("Semua", "Menunggu", "Diproses", "Selesai", "Ditolak")
    var filter by rememberSaveable { mutableStateOf("Semua") }
    val reports = DummyData.reports.filter {
        when (filter) {
            "Menunggu" -> it.status == ReportStatus.WAITING
            "Diproses" -> it.status == ReportStatus.PROCESS
            "Selesai" -> it.status == ReportStatus.DONE
            "Ditolak" -> it.status == ReportStatus.REJECTED
            else -> true
        }
    }

    MainScaffold(selected = Routes.MY_REPORTS, onNavigate = onNavigate) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ScreenHeader(onBack = onBack, title = "Laporan Saya")
            FilterChips(options = filters, selected = filter, onSelected = { filter = it })
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reports, key = { it.id }) { report ->
                    ReportCard(report = report, onClick = { onReportClick(report.id) })
                }
            }
        }
    }
}