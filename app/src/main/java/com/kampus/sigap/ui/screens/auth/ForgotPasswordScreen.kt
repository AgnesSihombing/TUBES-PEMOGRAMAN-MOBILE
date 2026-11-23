package com.kampus.sigap.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapAuthTopBar
import com.kampus.sigap.ui.components.SigapOutlinedButton
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, onOpenEmail: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var seconds by remember { mutableIntStateOf(30) }
    var restart by remember { mutableIntStateOf(0) }

    LaunchedEffect(restart) {
        seconds = 30
        while (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    Scaffold(containerColor = SigapBackground, topBar = { SigapAuthTopBar(onBack) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier.size(136.dp).clip(CircleShape).background(SigapTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Lock, null, tint = SigapPrimary, modifier = Modifier.size(64.dp))
            }
            Spacer(Modifier.height(32.dp))
            Text("Lupa Kata Sandi?", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(12.dp))
            Text(
                "Masukkan email kampus yang kamu gunakan.",
                fontSize = 16.sp,
                color = SigapText,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            SigapTextField(
                email, { email = it }, "Email Kampus",
                leadingIcon = Icons.Outlined.Email, keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(32.dp))
            SigapPrimaryButton(
                "Buka Email", onOpenEmail, Modifier.fillMaxWidth(),
                enabled = email.contains("@")
            )
            Spacer(Modifier.height(12.dp))
            SigapOutlinedButton(
                text = if (seconds > 0) "Kirim Ulang Email (${seconds}s)" else "Kirim Ulang Email",
                onClick = { restart += 1 },
                modifier = Modifier.fillMaxWidth(),
                enabled = seconds == 0
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    SigapTheme { ForgotPasswordScreen({}, {}) }
}