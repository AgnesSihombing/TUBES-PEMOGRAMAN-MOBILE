package com.kampus.sigapapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapLogo
import com.kampus.sigapapp.ui.theme.*

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onTerms: () -> Unit = {},
    onPrivacy: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(onBack = onBack, title = "Tentang SIGAP")
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            SigapLogo(white = false, modifier = Modifier.width(260.dp))
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Sistem Informasi Gangguan dan\nAduan Prasarana",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Versi 1.0.0",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Aplikasi untuk mempermudah pelaporan, pemantauan, dan penanganan gangguan atau kerusakan sarana dan prasarana di lingkungan kampus.",
                fontSize = 17.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
            Spacer(Modifier.height(28.dp))
            AboutRow(icon = Icons.Outlined.FactCheck, title = "Syarat & Ketentuan", onClick = onTerms)
            Spacer(Modifier.height(12.dp))
            AboutRow(icon = Icons.Outlined.VerifiedUser, title = "Kebijakan Privasi", onClick = onPrivacy)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AboutRow(icon: ImageVector, title: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppSurface)
            .border(1.dp, LineColor, shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryDark,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = BrandPrimary
        )
    }
}