package com.kampus.sigap.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.ui.components.SigapAuthTopBar
import com.kampus.sigap.ui.components.SigapDropdown
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.theme.*

private val ErrorColor = Color(0xFFE5484D)

@Composable
fun RegisterScreen(onBack: () -> Unit, onRegistered: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var userType by rememberSaveable { mutableStateOf("") }
    var idNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var submitted by rememberSaveable { mutableStateOf(false) }

    // Pesan error per kolom (null = tidak ada masalah)
    val nameError = if (name.isBlank() && submitted) "Nama lengkap wajib diisi" else null
    val userTypeError = if (userType.isBlank() && submitted) "Pilih jenis pengguna" else null
    val idError = if (idNumber.isBlank() && submitted) "NIM / NIP / ID Pegawai wajib diisi" else null
    val emailError = when {
        email.isBlank() && submitted -> "Email kampus wajib diisi"
        email.isNotBlank() && !(email.contains("@") && email.contains(".")) -> "Format email tidak valid"
        else -> null
    }
    val passwordError = when {
        password.isBlank() && submitted -> "Kata sandi wajib diisi"
        password.isNotEmpty() && password.length < 8 -> "Kata sandi minimal 8 karakter"
        else -> null
    }
    val confirmError = when {
        confirm.isBlank() && submitted -> "Konfirmasi kata sandi wajib diisi"
        confirm.isNotEmpty() && password != confirm -> "Kata sandi tidak sama"
        else -> null
    }

    val isValid = listOf(
        nameError, userTypeError, idError, emailError, passwordError, confirmError
    ).all { it == null } &&
            name.isNotBlank() && userType.isNotBlank() && idNumber.isNotBlank() &&
            email.isNotBlank() && password.isNotBlank() && confirm.isNotBlank()

    Scaffold(containerColor = SigapBackground, topBar = { SigapAuthTopBar(onBack) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Buat Akun", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(20.dp))

            SigapTextField(
                name, { name = it }, "Nama Lengkap",
                leadingIcon = Icons.Outlined.Person,
                errorText = nameError
            )
            Spacer(Modifier.height(16.dp))

            SigapDropdown(
                selected = userType,
                options = DummyData.userTypes,
                onSelected = { userType = it },
                placeholder = "Jenis Pengguna",
                leadingIcon = Icons.Outlined.ManageAccounts
            )
            if (userTypeError != null) {
                Spacer(Modifier.height(4.dp))
                Text(userTypeError, fontSize = 13.sp, color = ErrorColor)
            }
            Spacer(Modifier.height(16.dp))

            SigapTextField(
                idNumber, { idNumber = it }, "NIM / NIP / ID Pegawai",
                leadingIcon = Icons.Outlined.Badge,
                errorText = idError
            )
            Spacer(Modifier.height(16.dp))

            SigapTextField(
                email, { email = it }, "Email Kampus",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                errorText = emailError
            )
            Spacer(Modifier.height(16.dp))

            SigapTextField(
                password, { password = it }, "Kata Sandi",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                errorText = passwordError
            )
            Spacer(Modifier.height(16.dp))

            SigapTextField(
                confirm, { confirm = it }, "Konfirmasi Kata Sandi",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                errorText = confirmError
            )
            Spacer(Modifier.height(28.dp))

            SigapPrimaryButton(
                "Daftar",
                {
                    submitted = true
                    if (isValid) onRegistered()
                },
                Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    SigapTheme { RegisterScreen({}, {}) }
}