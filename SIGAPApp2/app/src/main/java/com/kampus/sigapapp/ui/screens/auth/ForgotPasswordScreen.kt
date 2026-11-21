package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.OutlineButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, onOpenEmail: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
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
            .imePadding()
    ) {
        ScreenHeader(onBack = onBack, showLogo = true)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(PrimarySoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Lupa Kata Sandi?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Masukkan email kampus yang kamu gunakan.",
                fontSize = 16.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            SigapTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email Kampus",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(32.dp))
            GradientButton(text = "Buka Email", onClick = onOpenEmail, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlineButton(
                text = if (seconds > 0) "Kirim Ulang Email (${seconds}s)" else "Kirim Ulang Email",
                onClick = { seconds = 30 },
                modifier = Modifier.fillMaxWidth(),
                enabled = seconds == 0
            )
        }
    }
}