package com.kampus.sigap.ui.screens.report


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.model.ReportStatus
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

private data class HistoryStep(val title: String, val time: String, val description: String)

private enum class DotState { Done, Current, Pending }

private val normalSteps = listOf(
    HistoryStep(ReportStatus.Waiting.label, "8 Nov 2026, 08.12", "Laporan berhasil dikirim."),
    HistoryStep(ReportStatus.Processed.label, "8 Nov 2026, 10.30", "Laporan telah diverifikasi dan akan ditindaklanjuti."),
    HistoryStep(ReportStatus.InProgress.label, "8 Nov 2026, 14.20", "Teknisi sedang melakukan perbaikan."),
    HistoryStep(ReportStatus.Done.label, "9 Nov 2026, 09.00", "Perbaikan telah selesai dilakukan.")
)

private val rejectedSteps = listOf(
    normalSteps[0],
    HistoryStep(ReportStatus.Rejected.label, "8 Nov 2026, 10.30", "Laporan tidak dapat diproses oleh admin.")
)

@Composable
fun ReportHistoryScreen(
    reportId: String,
    onBack: () -> Unit,
    onFeedback: () -> Unit
) {
    val report = DummyData.findReport(reportId)
    val status = report?.status ?: ReportStatus.Waiting
    val steps = if (status == ReportStatus.Rejected) rejectedSteps else normalSteps
    val currentIndex = when (status) {
        ReportStatus.Waiting -> 0
        ReportStatus.Processed -> 1
        ReportStatus.InProgress -> 2
        ReportStatus.Done -> 3
        ReportStatus.Rejected -> 1
    }
    val accent = if (status == ReportStatus.Rejected) SigapError else SigapPrimary

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Riwayat Penanganan", onBack) },
        bottomBar = {
            Box(Modifier.fillMaxWidth().navigationBarsPadding().padding(20.dp)) {
                SigapPrimaryButton(
                    "Beri Feedback",
                    onFeedback,
                    Modifier.fillMaxWidth(),
                    enabled = status == ReportStatus.Done
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            steps.forEachIndexed { index, step ->
                val dotState = when {
                    index < currentIndex -> DotState.Done
                    index == currentIndex && status == ReportStatus.Done -> DotState.Done
                    index == currentIndex -> DotState.Current
                    else -> DotState.Pending
                }
                val reached = index <= currentIndex
                val isLast = index == steps.lastIndex
                Row(Modifier.height(IntrinsicSize.Min)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TimelineDot(dotState, accent)
                        if (!isLast) {
                            Box(
                                Modifier
                                    .width(2.dp)
                                    .weight(1f)
                                    .background(if (index < currentIndex) accent else Color(0xFFCBD5DC))
                            )
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.padding(bottom = 32.dp)) {
                        Text(step.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SigapText)
                        Text(if (reached) step.time else "-", fontSize = 14.sp, color = SigapTextMuted)
                        if (reached) {
                            Spacer(Modifier.height(4.dp))
                            Text(step.description, fontSize = 16.sp, color = SigapText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineDot(state: DotState, accent: Color) {
    val background = if (state == DotState.Pending) Color(0xFFCBD5DC) else accent
    Box(
        Modifier.size(30.dp).clip(CircleShape).background(background),
        contentAlignment = Alignment.Center
    ) {
        if (state == DotState.Done) {
            Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(18.dp))
        } else {
            Box(Modifier.size(11.dp).clip(CircleShape).background(Color.White))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReportHistoryScreenPreview() {
    SigapTheme { ReportHistoryScreen(DummyData.reports.first().id, {}, {}) }
}