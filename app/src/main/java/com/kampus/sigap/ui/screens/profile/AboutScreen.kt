package com.kampus.sigap.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

@Composable
fun AboutScreen(onBack: () -> Unit, onTerms: () -> Unit, onPrivacy: () -> Unit) {
    Scaffold(containerColor = SigapBackground, topBar = { SigapTopBar("Tentang SIGAP", onBack) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Image(
                painter = painterResource(R.drawable.logo_sigap_hijau),
                contentDescription = "Logo SIGAP",
                modifier = Modifier.width(260.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Sistem Informasi Gangguan dan Aduan Prasarana",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SigapText,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            Text("Versi 1.0.0", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SigapText)
            Spacer(Modifier.height(20.dp))
            Text(
                "Aplikasi untuk mempermudah pelaporan, pemantauan, dan penanganan gangguan atau kerusakan sarana dan prasarana di lingkungan kampus.",
                fontSize = 17.sp,
                color = SigapText,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
            Spacer(Modifier.height(28.dp))
            AboutMenuItem(Icons.Outlined.FactCheck, "Syarat & Ketentuan", onTerms)
            Spacer(Modifier.height(12.dp))
            AboutMenuItem(Icons.Outlined.VerifiedUser, "Kebijakan Privasi", onPrivacy)
        }
    }
}

@Composable
private fun AboutMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = SigapSurface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(Modifier.padding(horizontal = 16.dp, vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = SigapPrimaryDark, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(14.dp))
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SigapText, modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = SigapPrimary)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AboutScreenPreview() {
    SigapTheme { AboutScreen({}, {}, {}) }
}