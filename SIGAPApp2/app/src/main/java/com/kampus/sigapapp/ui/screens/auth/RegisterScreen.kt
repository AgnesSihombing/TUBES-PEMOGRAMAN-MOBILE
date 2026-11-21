package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapDropdown
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.AppBackground
import com.kampus.sigapapp.ui.theme.TextDark

@Composable
fun RegisterScreen(onBack: () -> Unit, onRegistered: () -> Unit) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var userType by rememberSaveable { mutableStateOf("") }
    var userId by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(onBack = onBack, showLogo = true)
        Column(Modifier.padding(horizontal = 20.dp)) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Buat Akun", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(20.dp))
            SigapTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Nama Lengkap",
                leadingIcon = Icons.Outlined.Person
            )
            Spacer(Modifier.height(16.dp))
            SigapDropdown(
                value = userType,
                options = DummyData.userTypes,
                onSelected = { userType = it },
                placeholder = "Jenis Pengguna",
                leadingIcon = Icons.Outlined.ManageAccounts
            )
            Spacer(Modifier.height(16.dp))
            SigapTextField(
                value = userId,
                onValueChange = { userId = it },
                placeholder = "NIM / NIP / ID Pegawai",
                leadingIcon = Icons.Outlined.Badge,
                keyboardType = KeyboardType.Number
            )
            Spacer(Modifier.height(16.dp))
            SigapTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email Kampus",
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
            Spacer(Modifier.height(16.dp))
            SigapTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Konfirmasi Kata Sandi",
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true
            )
            Spacer(Modifier.height(40.dp))
            GradientButton(text = "Daftar", onClick = onRegistered, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(24.dp))
        }
    }
}