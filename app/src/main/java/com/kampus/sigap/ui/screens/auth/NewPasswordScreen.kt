package com.kampus.sigap.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapAuthTopBar
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.theme.*

@Composable
fun NewPasswordScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    title: String = "Atur Kata Sandi Baru",
    subtitle: String = "Buat kata sandi baru untuk akunmu."
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    val lengthOk = password.length >= 8
    val mixOk = password.any { it.isLetter() } && password.any { it.isDigit() }
    val symbolOk = password.any { !it.isLetterOrDigit() }
    val mismatch = confirm.isNotEmpty() && password != confirm
    val canSave = lengthOk && mixOk && password == confirm

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
                Icon(Icons.Filled.LockOpen, null, tint = SigapPrimary, modifier = Modifier.size(64.dp))
            }
            Spacer(Modifier.height(28.dp))
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(12.dp))
            Text(subtitle, fontSize = 16.sp, color = SigapText, textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            SigapTextField(password, { password = it }, "Kata Sandi", leadingIcon = Icons.Outlined.Lock, isPassword = true)
            Spacer(Modifier.height(16.dp))
            SigapTextField(
                confirm, { confirm = it }, "Konfirmasi Kata Sandi",
                leadingIcon = Icons.Outlined.Lock, isPassword = true,
                errorText = if (mismatch) "Kata sandi tidak sama" else null
            )
            Spacer(Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SigapTint)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Info, null, tint = SigapPrimaryDark, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Ketentuan kata sandi:", fontWeight = FontWeight.Bold, color = SigapPrimaryDark, fontSize = 14.sp)
                }
                RuleItem("Minimal 8 karakter", lengthOk)
                RuleItem("Mengandung huruf dan angka", mixOk)
                RuleItem("Disarankan menggunakan simbol (mis. !, @, #)", symbolOk)
            }
            Spacer(Modifier.height(28.dp))
            SigapPrimaryButton("Simpan Kata Sandi", onSaved, Modifier.fillMaxWidth(), enabled = canSave)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun RuleItem(text: String, satisfied: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (satisfied) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (satisfied) SigapPrimary else SigapTextMuted,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 13.sp, color = if (satisfied) SigapPrimaryDark else SigapTextMuted)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewPasswordScreenPreview() {
    SigapTheme { NewPasswordScreen({}, {}) }
}