package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.ReportStatus
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.theme.*

private data class HistoryStep(val title: String, val time: String, val description: String)

private val historySteps = listOf(
    HistoryStep("Menunggu Verifikasi", "8 Nov 2026, 08.12", "Laporan berhasil dikirim."),
    HistoryStep("Diproses", "8 Nov 2026, 10.30", "Laporan telah diverifikasi dan akan ditindaklanjuti."),
    HistoryStep("Dalam Pengerjaan", "8 Nov 2026, 14.20", "Teknisi sedang melakukan perbaikan."),
    HistoryStep("Selesai", "9 Nov 2026, 16.00", "Perbaikan telah selesai dilakukan.")
)

@Composable
fun ReportHistoryScreen(reportId: String, onBack: () -> Unit, onFeedback: () -> Unit) {
    val report = DummyData.findReport(reportId)
    val currentIndex = when (report.status) {
        ReportStatus.WAITING -> 0
        ReportStatus.REJECTED -> 1
        ReportStatus.PROCESS -> 2
        ReportStatus.DONE -> historySteps.size
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ScreenHeader(onBack = onBack, title = "Riwayat Penanganan")
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            historySteps.forEachIndexed { index, step ->
                TimelineItem(
                    step = step,
                    index = index,
                    currentIndex = currentIndex,
                    isLast = index == historySteps.lastIndex
                )
            }
        }
        GradientButton(
            text = "Beri Feedback",
            onClick = onFeedback,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            enabled = report.status == ReportStatus.DONE
        )
    }
}

@Composable
private fun TimelineItem(step: HistoryStep, index: Int, currentIndex: Int, isLast: Boolean) {
    val reached = index <= currentIndex
    val finished = index < currentIndex

    Row(Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier.width(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (reached) BrandPrimary else StepInactive),
                contentAlignment = Alignment.Center
            ) {
                if (finished) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Box(
                        Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
            if (!isLast) {
                Box(
                    Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(if (finished) BrandPrimary else StepInactive)
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.padding(bottom = if (isLast) 0.dp else 28.dp)) {
            Text(
                text = step.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(4.dp))
            if (reached) {
                Text(text = step.time, fontSize = 14.sp, color = TextGray)
                Spacer(Modifier.height(6.dp))
                Text(
                    text = step.description,
                    fontSize = 16.sp,
                    color = TextDark,
                    lineHeight = 22.sp
                )
            } else {
                Text(text = "-", fontSize = 16.sp, color = TextGray)
            }
        }
    }
}