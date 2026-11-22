package com.kampus.sigap.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.theme.*

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMe by rememberSaveable { mutableStateOf(false) }
    val canLogin = email.isNotBlank() && password.isNotBlank()

    Scaffold(containerColor = SigapBackground) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(56.dp))
                Image(
                    painter = painterResource(R.drawable.logo_sigap_hijau),
                    contentDescription = "Logo SIGAP",
                    modifier = Modifier.width(200.dp)
                )
                Spacer(Modifier.height(40.dp))
                Text("Masuk ke Akunmu", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
                Spacer(Modifier.height(20.dp))
                Text(
                    "Gunakan email kampus untuk mengakses SIGAP.",
                    fontSize = 16.sp,
                    color = SigapText,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
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
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = SigapPrimary)
                    )
                    Text(
                        "Ingat saya",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SigapText,
                        modifier = Modifier.clickable { rememberMe = !rememberMe }
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        "Lupa kata sandi?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SigapPrimary,
                        modifier = Modifier.clickable(onClick = onForgotPassword)
                    )
                }
                Spacer(Modifier.height(16.dp))
                SigapPrimaryButton("Masuk", onLogin, Modifier.fillMaxWidth(), enabled = canLogin)
                Spacer(Modifier.height(96.dp))
            }
            Row(
                Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Belum punya akun? ", fontSize = 16.sp, color = SigapText)
                Text(
                    "Daftar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SigapPrimary,
                    modifier = Modifier.clickable(onClick = onRegister)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    SigapTheme { LoginScreen({}, {}, {}) }
}