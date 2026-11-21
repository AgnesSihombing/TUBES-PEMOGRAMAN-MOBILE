package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.SigapLogo
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(64.dp))
            SigapLogo(
                white = false,
                modifier = Modifier
                    .width(230.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Masuk ke Akunmu",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Gunakan email kampus untuk mengakses SIGAP.",
                fontSize = 16.sp,
                color = TextDark
            )
            Spacer(Modifier.height(24.dp))
            SigapTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(16.dp))
            SigapTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Kata Sandi",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable { rememberMe = !rememberMe },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = BrandPrimary)
                    )
                    Text(text = "Ingat saya", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                }
                Text(
                    text = "Lupa kata sandi?",
                    modifier = Modifier.clickable(onClick = onForgotPassword),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
            }
            Spacer(Modifier.height(12.dp))
            GradientButton(text = "Masuk", onClick = onLogin, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(100.dp))
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Belum punya akun? ", fontSize = 16.sp, color = TextDark)
            Text(
                text = "Daftar",
                modifier = Modifier.clickable(onClick = onRegister),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BrandPrimary
            )
        }
    }
}