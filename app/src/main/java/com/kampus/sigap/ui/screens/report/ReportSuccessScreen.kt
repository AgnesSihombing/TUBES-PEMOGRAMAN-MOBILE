package com.kampus.sigap.ui.screens.report

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapOutlinedButton
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.theme.*

@Composable
fun ReportSuccessScreen(
    reportNumber: String,
    onViewReports: () -> Unit,
    onBackHome: () -> Unit
) {
    BackHandler(onBack = onBackHome)

    val inspection = LocalInspectionMode.current
    val badgeScale = remember { Animatable(if (inspection) 1f else 0f) }
    val content = remember { Animatable(if (inspection) 1f else 0f) }

    LaunchedEffect(Unit) {
        if (!inspection) {
            badgeScale.animateTo(1f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow))
            content.animateTo(1f, tween(500))
        }
    }

    Scaffold(containerColor = SigapBackground) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(96.dp))
            Box(
                Modifier
                    .size(104.dp)
                    .graphicsLayer { scaleX = badgeScale.value; scaleY = badgeScale.value }
                    .clip(CircleShape)
                    .background(SigapGradientDiagonal),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(60.dp))
            }
            Spacer(Modifier.height(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.graphicsLayer {
                    alpha = content.value
                    translationY = (1f - content.value) * 40f
                }
            ) {
                Text(
                    "Laporan Berhasil Dikirim!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SigapText
                )
                Spacer(Modifier.height(24.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SigapTint)
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Nomor Laporan", fontSize = 16.sp, color = SigapText)
                    Spacer(Modifier.height(4.dp))
                    Text("#$reportNumber", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    "Laporanmu sedang menunggu verifikasi oleh admin. Kamu akan mendapat notifikasi perkembangannya.",
                    fontSize = 16.sp,
                    color = SigapText,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
            Spacer(Modifier.weight(1f))
            SigapPrimaryButton("Lihat Laporan", onViewReports, Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            SigapOutlinedButton("Kembali ke Beranda", onBackHome, Modifier.fillMaxWidth())
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReportSuccessScreenPreview() {
    SigapTheme { ReportSuccessScreen("LP-20261108-001", {}, {}) }
}