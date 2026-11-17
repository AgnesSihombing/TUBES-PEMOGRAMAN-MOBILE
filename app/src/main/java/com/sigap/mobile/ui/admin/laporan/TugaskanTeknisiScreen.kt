package com.sigap.mobile.ui.admin.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Laporan
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.SigapButton
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary
import com.sigap.mobile.ui.theme.StatusDitolakFg

private const val MAX_CATATAN = 300

// Data dummy. Nanti daftar teknisi diambil dari Firebase.
private val dummyTeknisi = listOf("Windah", "Budi Santoso", "Rudi Hartono")

@Composable
fun TugaskanTeknisiScreen(
    laporan: Laporan = Laporan(
        "#LP-20261108-001", "AC tidak dingin", "Gedung A, Lantai 2, Ruang A201",
        LaporanStatus.MenungguVerifikasi
    ),
    onBack: () -> Unit = {},
    onTugaskan: (teknisi: String, catatan: String) -> Unit = { _, _ -> },
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // State: teknisi yang dipilih (null = belum dipilih), catatan, dan status menu dropdown
    var teknisi by remember { mutableStateOf<String?>(null) }
    var catatan by remember { mutableStateOf("") }
    var menuTerbuka by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Laporan, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Kembali")
                }
                Text("Tugaskan Teknisi", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))

            // Laporan: hanya baca
            Text("Laporan", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "${laporan.id} - ${laporan.judul}",
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            )

            Spacer(Modifier.height(20.dp))

            // Pilih teknisi (wajib)
            Row {
                Text("Pilih Teknisi", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(" *", fontSize = 13.sp, color = StatusDitolakFg)
            }
            Spacer(Modifier.height(6.dp))
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                        .clickable { menuTerbuka = true }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = teknisi ?: "Pilih teknisi",
                        fontSize = 13.sp,
                        color = if (teknisi == null) SigapTextSecondary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Outlined.KeyboardArrowDown, null)
                }
                DropdownMenu(expanded = menuTerbuka, onDismissRequest = { menuTerbuka = false }) {
                    dummyTeknisi.forEach { nama ->
                        DropdownMenuItem(
                            text = { Text(nama) },
                            onClick = {
                                teknisi = nama
                                menuTerbuka = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Catatan penugasan, dibatasi 300 karakter
            Text("Catatan Penugasan", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it.take(MAX_CATATAN) },
                placeholder = { Text("Contoh: Periksa kondisi AC dan lakukan perbaikan.", fontSize = 13.sp) },
                modifier = Modifier.fillMaxWidth().height(130.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
            Text(
                text = "${catatan.length}/$MAX_CATATAN",
                fontSize = 11.sp,
                color = SigapTextSecondary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )

            // Dorong tombol ke bagian bawah layar
            Spacer(Modifier.weight(1f))

            SigapButton(
                text = "Tugaskan",
                enabled = teknisi != null, // aktif hanya jika teknisi sudah dipilih
                onClick = { teknisi?.let { onTugaskan(it, catatan) } }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun TugaskanTeknisiPreview() {
    SIGAPTheme { TugaskanTeknisiScreen() }
}