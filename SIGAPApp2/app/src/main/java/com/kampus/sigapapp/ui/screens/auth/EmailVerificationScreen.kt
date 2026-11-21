package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.OutlineButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun EmailVerificationScreen(onBack: () -> Unit, onOpenEmail: () -> Unit) {
    var seconds by remember { mutableIntStateOf(30) }

    LaunchedEffect(seconds) {
        if (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(onBack = onBack)
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(135.dp)
                    .clip(CircleShape)
                    .background(PrimarySoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Verifikasi Email",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Email verifikasi telah dikirim ke alamat email kampus yang Anda daftarkan.",
                fontSize = 16.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(24.dp))
            StepRow(
                number = "1",
                title = "Buka email kampus Anda",
                description = "Periksa kotak masuk email kampus."
            )
            Spacer(Modifier.height(16.dp))
            StepRow(
                number = "2",
                title = "Klik tautan verifikasi",
                description = "Buka email dari SIGAP, lalu klik tautan verifikasi untuk mengaktifkan akun."
            )
            Spacer(Modifier.height(24.dp))
            val infoShape = RoundedCornerShape(12.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(infoShape)
                    .background(FieldBg)
                    .border(1.dp, LineColor, infoShape)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Belum menemukan emailnya?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Periksa folder spam atau junk. Jika belum ada, kirim ulang email verifikasi.",
                        fontSize = 14.sp,
                        color = TextGray,
                        lineHeight = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            GradientButton(text = "Buka Email", onClick = onOpenEmail, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlineButton(
                text = if (seconds > 0) "Kirim Ulang Email (${seconds}s)" else "Kirim Ulang Email",
                onClick = { seconds = 30 },
                modifier = Modifier.fillMaxWidth(),
                enabled = seconds == 0
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Setelah verifikasi berhasil, kembali ke SIGAP untuk masuk ke akun Anda.",
                fontSize = 13.sp,
                color = TextGray,
                textAlign = TextAlign.Center,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StepRow(number: String, title: String, description: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(PrimarySoft),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
        }
    }
}