package com.example.sigap.ui.screens.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

// ---------- Warna lokal ----------
private val TextPrimary = Color(0xFF1F2937)
private val TextSecondary = Color(0xFF4B5563)
private val TextHint = Color(0xFF9CA3AF)
private val PendingColor = Color(0xFFD1D5DB)

// ---------- Model data ----------
private enum class TimelineState { COMPLETED, ACTIVE, PENDING }

private data class TimelineStep(
    val title: String,
    val dateTime: String,
    val description: String,
    val state: TimelineState
)

private val sampleSteps = listOf(
    TimelineStep("Menunggu Verifikasi", "8 Nov 2026, 08.12", "Laporan berhasil dikirim.", TimelineState.COMPLETED),
    TimelineStep("Diproses", "8 Nov 2026, 10.30", "Laporan telah diverifikasi dan akan ditindaklanjuti.", TimelineState.COMPLETED),
    TimelineStep("Dalam Pengerjaan", "8 Nov 2026, 14.20", "Teknisi sedang melakukan perbaikan.", TimelineState.ACTIVE),
    TimelineStep("Selesai", "-", "", TimelineState.PENDING)
)

// ---------- Screen ----------
@Composable
fun TimelineScreen(
    navController: NavHostController
) {
    val steps = sampleSteps
    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            Button(
                onClick = { navController.navigate("feedback") },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SigapPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Beri Feedback",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Riwayat Penanganan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(Modifier.height(24.dp))

            steps.forEachIndexed { index, step ->
                TimelineItem(step = step, isLast = index == steps.lastIndex)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ---------- Item timeline ----------
@Composable
private fun TimelineItem(step: TimelineStep, isLast: Boolean) {
    // IntrinsicSize.Min membuat garis vertikal otomatis setinggi teks di kanannya
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(28.dp)
                .fillMaxHeight()
        ) {
            TimelineDot(state = step.state)

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .width(2.dp)
                        .weight(1f)
                        .background(
                            if (step.state == TimelineState.COMPLETED) SigapPrimary
                            else PendingColor
                        )
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 0.dp else 24.dp)
        ) {
            Text(
                text = step.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(2.dp))
            Text(text = step.dateTime, fontSize = 11.sp, color = TextHint)
            if (step.description.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = step.description,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun TimelineDot(state: TimelineState) {
    when (state) {
        // Selesai: lingkaran hijau + centang
        TimelineState.COMPLETED -> Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(SigapPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }

        // Sedang berjalan: lingkaran hijau + titik putih
        TimelineState.ACTIVE -> Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(SigapPrimary),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }

        // Belum: lingkaran abu-abu + titik putih
        TimelineState.PENDING -> Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(PendingColor),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimelineScreenPreview() {
    SIGAPTheme {
        TimelineScreen(rememberNavController())
    }
}
