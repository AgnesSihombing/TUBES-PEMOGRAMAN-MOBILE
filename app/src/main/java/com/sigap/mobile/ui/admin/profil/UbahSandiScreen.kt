package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.*
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.StatusDitolakFg

private const val MIN_SANDI = 8

@Composable
fun UbahSandiScreen(
    onBack: () -> Unit = {},
    onSimpan: (sandiLama: String, sandiBaru: String) -> Unit = { _, _ -> },
    onTabSelected: (AdminTab) -> Unit = {}
) {
    var lama by remember { mutableStateOf("") }
    var baru by remember { mutableStateOf("") }
    var konfirmasi by remember { mutableStateOf("") }
    var tampil by remember { mutableStateOf(false) }

    // Validasi langsung saat mengetik
    val terlaluPendek = baru.isNotEmpty() && baru.length < MIN_SANDI
    val tidakCocok = konfirmasi.isNotEmpty() && konfirmasi != baru
    val valid = lama.isNotBlank() && baru.length >= MIN_SANDI && konfirmasi == baru

    val transformasi = if (tampil) VisualTransformation.None else PasswordVisualTransformation()
    val keyboard = KeyboardOptions(keyboardType = KeyboardType.Password)
    val ikonMata: @Composable () -> Unit = {
        IconButton(onClick = { tampil = !tampil }) {
            Icon(
                if (tampil) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = "Tampilkan sandi"
            )
        }
    }

    SigapSubScreen("Ubah Kata Sandi", onBack, onTabSelected) {
        FieldLabel("Kata sandi saat ini")
        SigapTextField(
            lama, { lama = it }, "Kata sandi saat ini", Modifier.fillMaxWidth(),
            trailingContent = ikonMata, visualTransformation = transformasi, keyboardOptions = keyboard
        )

        Spacer(Modifier.height(16.dp))
        FieldLabel("Kata sandi baru")
        SigapTextField(
            baru, { baru = it }, "Minimal $MIN_SANDI karakter", Modifier.fillMaxWidth(),
            trailingContent = ikonMata, visualTransformation = transformasi, keyboardOptions = keyboard
        )
        if (terlaluPendek) PesanError("Kata sandi minimal $MIN_SANDI karakter")

        Spacer(Modifier.height(16.dp))
        FieldLabel("Konfirmasi kata sandi baru")
        SigapTextField(
            konfirmasi, { konfirmasi = it }, "Ulangi kata sandi baru", Modifier.fillMaxWidth(),
            trailingContent = ikonMata, visualTransformation = transformasi, keyboardOptions = keyboard
        )
        if (tidakCocok) PesanError("Konfirmasi tidak sama dengan kata sandi baru")

        Spacer(Modifier.height(24.dp))
        SigapButton("Simpan Kata Sandi", enabled = valid, onClick = { onSimpan(lama, baru) })
    }
}

@Composable
private fun PesanError(teks: String) {
    Text(teks, fontSize = 11.sp, color = StatusDitolakFg, modifier = Modifier.padding(top = 4.dp))
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun UbahSandiPreview() {
    SIGAPTheme { UbahSandiScreen() }
}