package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*

@Composable
fun NewPasswordScreen(onBack: () -> Unit, onSaved: () -> Unit) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val infoShape = RoundedCornerShape(12.dp)

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
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
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
                    imageVector = Icons.Filled.LockOpen,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Atur Kata Sandi Baru",
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
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(infoShape)
                    .background(PrimarySoft)
                    .border(1.dp, LineColor, infoShape)
                    .padding(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Ketentuan kata sandi:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDark
                    )
                    Text(text = "•  Minimal 8 karakter", fontSize = 14.sp, color = PrimaryDark)
                    Text(text = "•  Mengandung huruf dan angka", fontSize = 14.sp, color = PrimaryDark)
                    Text(
                        text = "•  Disarankan menggunakan simbol (mis. !, @, #)",
                        fontSize = 14.sp,
                        color = PrimaryDark
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            GradientButton(
                text = "Simpan Kata Sandi",
                onClick = onSaved,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}