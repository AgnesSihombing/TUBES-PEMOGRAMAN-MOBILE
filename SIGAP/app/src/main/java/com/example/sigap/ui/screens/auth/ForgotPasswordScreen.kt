package com.example.sigap.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.R
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

private val TextPrimary = Color(0xFF1F2937)
private val TextSecondary = Color(0xFF4B5563)
private val TextHint = Color(0xFF9CA3AF)
private val BorderColor = Color(0xFFE5E7EB)
private val FieldBg = Color(0xFFF9FAFB)
private val LightGreenBg = Color(0xFFF0FDF4)
private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    onBackClick: () -> Unit = { navController.popBackStack() },
    onOpenEmailClick: (email: String) -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var isEmailSent by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Kembali",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Ilustrasi Gembok menggunakan aset ic_lock_forgot
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(LightGreenBg, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_lock_forgot),
                    contentDescription = "Ikon Gembok",
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Lupa Kata Sandi?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Masukkan email kampus yang kamu gunakan.",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Input Email Kampus
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                supportingText = emailError?.let { { Text(it, fontSize = 11.sp) } },
                placeholder = { Text("Email Kampus", color = TextHint, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(18.dp)
                    )
                },
                textStyle = TextStyle(fontSize = 13.sp, color = TextPrimary),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SigapPrimary,
                    unfocusedBorderColor = BorderColor,
                    focusedContainerColor = FieldBg,
                    unfocusedContainerColor = FieldBg,
                    cursorColor = SigapPrimary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Buka Email
            Button(
                onClick = {
                    val cleanEmail = email.trim()
                    emailError = when {
                        cleanEmail.isEmpty() -> "Email wajib diisi"
                        !emailRegex.matches(cleanEmail) -> "Format email tidak valid"
                        else -> null
                    }
                    if (emailError == null) {
                        isEmailSent = true
                        onOpenEmailClick(cleanEmail)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SigapPrimary)
            ) {
                Text(
                    text = "Buka Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Kirim Ulang Email
            OutlinedButton(
                onClick = { /* Aksi kirim ulang */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SigapPrimary)
            ) {
                Text(
                    text = if (isEmailSent) "Kirim Ulang Email (30s)" else "Kirim Ulang Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordPreview() {
    SIGAPTheme {
        ForgotPasswordScreen(navController = rememberNavController())
    }
}