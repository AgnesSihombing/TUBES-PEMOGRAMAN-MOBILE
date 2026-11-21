package com.kampus.sigapapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*

@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    MainScaffold(selected = Routes.PROFILE, onNavigate = onNavigate) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(onBack = onBack, title = "Ubah Kata Sandi")
            Column(Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Jaga keamanan akun Anda dengan mengganti kata sandi secara berkala.",
                    fontSize = 16.sp,
                    color = TextGray,
                    lineHeight = 24.sp
                )
                Spacer(Modifier.height(20.dp))
                FieldLabel("Kata Sandi Saat Ini")
                SigapTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    placeholder = "Masukkan kata sandi saat ini",
                    isPassword = true,
                    minHeight = 56.dp
                )
                Spacer(Modifier.height(20.dp))
                FieldLabel("Kata Sandi Baru")
                SigapTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = "Masukkan kata sandi baru",
                    isPassword = true,
                    minHeight = 56.dp
                )
                Spacer(Modifier.height(20.dp))
                FieldLabel("Konfirmasi Kata Sandi Baru")
                SigapTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Ulangi kata sandi baru",
                    isPassword = true,
                    minHeight = 56.dp
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimarySoft)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = BrandPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Gunakan minimal 8 karakter dengan kombinasi huruf besar, huruf kecil, dan angka.",
                        fontSize = 13.sp,
                        color = TextGray,
                        lineHeight = 19.sp
                    )
                }
                Spacer(Modifier.height(20.dp))
                GradientButton(
                    text = "Simpan Perubahan",
                    onClick = onSaved,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 8.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = TextDark
    )
}