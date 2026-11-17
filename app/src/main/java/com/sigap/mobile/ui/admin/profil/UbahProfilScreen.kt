package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.*
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

@Composable
fun UbahProfilScreen(
    namaAwal: String = "Rina Wulandari",
    email: String = "admin@kampus.ac.id",
    onBack: () -> Unit = {},
    onSimpan: (nama: String) -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    var nama by remember { mutableStateOf(namaAwal) }

    SigapSubScreen("Ubah Profil", onBack, onTabSelected) {
        FieldLabel("Nama lengkap")
        SigapTextField(nama, { nama = it }, "Nama lengkap", Modifier.fillMaxWidth())

        Spacer(Modifier.height(16.dp))
        FieldLabel("Email kampus")
        // Email hanya tampil, tidak bisa diubah
        Text(
            email,
            fontSize = 14.sp,
            color = SigapTextSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                .padding(16.dp)
        )
        Text(
            "Email tidak dapat diubah.",
            fontSize = 11.sp,
            color = SigapTextSecondary,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(Modifier.height(24.dp))
        SigapButton(
            text = "Simpan Perubahan",
            enabled = nama.isNotBlank(),
            onClick = { onSimpan(nama.trim()) }
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun UbahProfilPreview() {
    SIGAPTheme { UbahProfilScreen() }
}