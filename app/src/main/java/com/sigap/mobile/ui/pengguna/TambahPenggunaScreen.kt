package com.sigap.mobile.ui.admin.pengguna

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Peran
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.SigapButton
import com.sigap.mobile.ui.components.SigapCard
import com.sigap.mobile.ui.components.SigapTextField
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

@Composable
fun TambahPenggunaScreen(
    onBack: () -> Unit = {},
    onSimpan: (nama: String, id: String, email: String, peran: Peran, aktif: Boolean) -> Unit =
        { _, _, _, _, _ -> },
    onTabSelected: (AdminTab) -> Unit = {}
) {
    var nama by remember { mutableStateOf("") }
    var idUser by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var peran by remember { mutableStateOf(Peran.Mahasiswa) }
    var aktif by remember { mutableStateOf(true) }

    // Tombol baru aktif jika semua kolom wajib terisi
    val formValid = nama.isNotBlank() && idUser.isNotBlank() && email.isNotBlank()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Pengguna, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Bagian form bisa di-scroll, tombol tetap di bawah
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Kembali")
                    }
                    Text("Tambah Pengguna", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                    "Lengkapi data untuk membuat akun kampus baru.",
                    fontSize = 12.sp, color = SigapTextSecondary
                )

                SigapCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Badge, null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Text("Identitas pengguna", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(12.dp))
                    FieldLabel("Nama lengkap")
                    SigapTextField(
                        value = nama, onValueChange = { nama = it },
                        placeholder = "Nama lengkap", modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    FieldLabel("NIM / NIP / ID")
                    SigapTextField(
                        value = idUser, onValueChange = { idUser = it },
                        placeholder = "NIM / NIP / ID", modifier = Modifier.fillMaxWidth()
                    )
                }

                SigapCard {
                    Text("Akun dan akses", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    FieldLabel("Email kampus")
                    SigapTextField(
                        value = email, onValueChange = { email = it },
                        placeholder = "nama@kampus.ac.id",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    FieldLabel("Peran pengguna")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Peran.entries.forEach { p ->
                            PeranOption(p.label, selected = peran == p) { peran = p }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Aktifkan akun", fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Switch(checked = aktif, onCheckedChange = { aktif = it })
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Info, null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Admin dapat mengelola pengguna dan laporan. Tautan buat kata sandi dikirim melalui email.",
                            fontSize = 11.sp, color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            SigapButton(
                text = "Tambah Pengguna",
                enabled = formValid,
                onClick = { onSimpan(nama, idUser, email, peran, aktif) }
            )
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
}

// Pilihan peran: yang dipilih berwarna hijau penuh dan diberi tanda centang
@Composable
private fun PeranOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = if (selected) "✓ $label" else label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = if (selected) Color.White else SigapTextSecondary,
        modifier = Modifier
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                RoundedCornerShape(50)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 7.dp)
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun TambahPenggunaPreview() {
    SIGAPTheme { TambahPenggunaScreen() }
}