package com.kampus.sigap.ui.screens.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.BottomTab
import com.kampus.sigap.ui.components.SigapBottomBar
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit
) {
    var current by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    val strong = newPassword.length >= 8 &&
            newPassword.any { it.isUpperCase() } &&
            newPassword.any { it.isLowerCase() } &&
            newPassword.any { it.isDigit() }
    val newError = if (newPassword.isNotEmpty() && !strong) "Belum memenuhi ketentuan kata sandi" else null
    val confirmError = if (confirm.isNotEmpty() && confirm != newPassword) "Kata sandi tidak sama" else null
    val canSave = current.isNotBlank() && strong && newPassword == confirm

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Berhasil", fontWeight = FontWeight.Bold) },
            text = { Text("Kata sandi kamu berhasil diubah.") },
            confirmButton = {
                TextButton(onClick = { showSuccess = false; onSaved() }) {
                    Text("OK", color = SigapPrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Ubah Kata Sandi", onBack) },
        bottomBar = { SigapBottomBar(BottomTab.Profile, onTabClick, onAddClick) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                "Jaga keamanan akun Anda dengan mengganti kata sandi secara berkala.",
                fontSize = 16.sp,
                color = SigapTextMuted,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(20.dp))
            FieldTitle("Kata Sandi Saat Ini")
            SigapTextField(current, { current = it }, "Masukkan kata sandi saat ini", isPassword = true)
            Spacer(Modifier.height(16.dp))
            FieldTitle("Kata Sandi Baru")
            SigapTextField(
                newPassword, { newPassword = it }, "Masukkan kata sandi baru",
                isPassword = true, errorText = newError
            )
            Spacer(Modifier.height(16.dp))
            FieldTitle("Konfirmasi Kata Sandi Baru")
            SigapTextField(
                confirm, { confirm = it }, "Ulangi kata sandi baru",
                isPassword = true, errorText = confirmError
            )
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SigapTint)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Info, null, tint = SigapPrimary, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(10.dp))
                Text(
                    "Gunakan minimal 8 karakter dengan kombinasi huruf besar, huruf kecil, dan angka.",
                    fontSize = 14.sp,
                    color = SigapPrimaryDark,
                    lineHeight = 20.sp
                )
            }
            Spacer(Modifier.height(24.dp))
            SigapPrimaryButton("Simpan Perubahan", { showSuccess = true }, Modifier.fillMaxWidth(), enabled = canSave)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FieldTitle(text: String) {
    Text(text, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = SigapText)
    Spacer(Modifier.height(8.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChangePasswordScreenPreview() {
    SigapTheme { ChangePasswordScreen({}, {}, {}, {}) }
}