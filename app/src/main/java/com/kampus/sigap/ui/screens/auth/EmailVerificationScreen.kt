package com.kampus.sigap.ui.screens.auth


import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapOutlinedButton
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun EmailVerificationScreen(onBack: () -> Unit, onFinished: () -> Unit) {
    val context = LocalContext.current
    var seconds by remember { mutableIntStateOf(30) }
    var restart by remember { mutableIntStateOf(0) }

    LaunchedEffect(restart) {
        seconds = 30
        while (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    Scaffold(containerColor = SigapBackground, topBar = { SigapTopBar("", onBack) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(136.dp).clip(CircleShape).background(SigapTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Email, null, tint = SigapPrimaryDark, modifier = Modifier.size(72.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text("Verifikasi Email", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(12.dp))
            Text(
                "Email verifikasi telah dikirim ke alamat email kampus yang Anda daftarkan.",
                fontSize = 16.sp,
                color = SigapTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(24.dp))
            VerificationStep("1", "Buka email kampus Anda", "Periksa kotak masuk email kampus.")
            Spacer(Modifier.height(16.dp))
            VerificationStep(
                "2",
                "Klik tautan verifikasi",
                "Buka email dari SIGAP, lalu klik tautan verifikasi untuk mengaktifkan akun."
            )
            Spacer(Modifier.height(24.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, SigapBorder, RoundedCornerShape(12.dp))
                    .background(SigapFieldBg)
                    .padding(16.dp)
            ) {
                Icon(Icons.Outlined.Info, null, tint = SigapPrimaryDark, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Belum menemukan emailnya?", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SigapText)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Periksa folder spam atau junk. Jika belum ada, kirim ulang email verifikasi.",
                        fontSize = 14.sp,
                        color = SigapTextMuted,
                        lineHeight = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            SigapPrimaryButton(
                "Buka Email",
                {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_EMAIL)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Aplikasi email tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                },
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            SigapOutlinedButton(
                text = if (seconds > 0) "Kirim Ulang Email (${seconds}s)" else "Kirim Ulang Email",
                onClick = {
                    restart += 1
                    Toast.makeText(context, "Email verifikasi dikirim ulang", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = seconds == 0
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Setelah verifikasi berhasil, kembali ke SIGAP untuk masuk ke akun Anda.",
                fontSize = 13.sp,
                color = SigapTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.clickable(onClick = onFinished)
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VerificationStep(number: String, title: String, description: String) {
    Row(Modifier.fillMaxWidth()) {
        Box(
            Modifier.size(32.dp).clip(CircleShape).background(SigapTint),
            contentAlignment = Alignment.Center
        ) {
            Text(number, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SigapPrimaryDark)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = SigapText)
            Spacer(Modifier.height(2.dp))
            Text(description, fontSize = 14.sp, color = SigapTextMuted, lineHeight = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EmailVerificationScreenPreview() {
    SigapTheme { EmailVerificationScreen({}, {}) }
}