package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.OutlineButton
import com.kampus.sigapapp.ui.theme.*

@Composable
fun ReportSuccessScreen(onViewReport: () -> Unit, onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(110.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(GradientPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Laporan Berhasil Dikirim!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(PrimarySoft)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nomor Laporan", fontSize = 15.sp, color = TextDark)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "#LP-20261108-001",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Laporanmu sedang menunggu verifikasi oleh admin. Kamu akan mendapat notifikasi perkembangannya.",
            fontSize = 16.sp,
            color = TextDark,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        Spacer(Modifier.weight(1f))
        GradientButton(
            text = "Lihat Laporan",
            onClick = onViewReport,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlineButton(
            text = "Kembali ke Beranda",
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
    }
}