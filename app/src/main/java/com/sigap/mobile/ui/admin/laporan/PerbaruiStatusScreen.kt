package com.sigap.mobile.ui.admin.laporan

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
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
import android.net.Uri
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.SigapButton
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary
import com.sigap.mobile.ui.theme.StatusDitolakFg

private const val MAX_CATATAN = 200

// Status yang boleh dipilih saat memperbarui progres.
// "Ditolak" tidak ada di sini karena penolakan dilakukan saat verifikasi.
private val opsiStatus = listOf(LaporanStatus.Diproses, LaporanStatus.Selesai)

@Composable
fun PerbaruiStatusScreen(
    statusAwal: LaporanStatus = LaporanStatus.Diproses,
    onBack: () -> Unit = {},
    onSimpan: (status: LaporanStatus, catatan: String, foto: Uri?) -> Unit = { _, _, _ -> },
    onTabSelected: (AdminTab) -> Unit = {}
) {
    var status by remember { mutableStateOf(statusAwal) }
    var catatan by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var menuTerbuka by remember { mutableStateOf(false) }

    // Launcher = pembuka galeri. Hasilnya (Uri foto) masuk ke blok di bawah.
    // Tidak butuh izin khusus karena memakai photo picker bawaan Android.
    val pilihFoto = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> if (uri != null) fotoUri = uri }

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
                Text("Perbarui Status", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))

            // Dropdown status (wajib)
            Row {
                Text("Status", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
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
                    Text(status.label, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    Icon(Icons.Outlined.KeyboardArrowDown, null)
                }
                DropdownMenu(expanded = menuTerbuka, onDismissRequest = { menuTerbuka = false }) {
                    opsiStatus.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.label) },
                            onClick = {
                                status = item
                                menuTerbuka = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Catatan progres, dibatasi 200 karakter
            Text("Catatan Progres", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it.take(MAX_CATATAN) },
                placeholder = { Text("Contoh: Perbaikan sedang dilakukan.", fontSize = 13.sp) },
                modifier = Modifier.fillMaxWidth().height(110.dp),
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

            Spacer(Modifier.height(20.dp))

            // Foto bukti (opsional)
            Text("Foto Bukti (Opsional)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        RoundedCornerShape(10.dp)
                    )
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                    .clickable {
                        pilihFoto.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.Add, null, tint = MaterialTheme.colorScheme.primary)
                        Text(
                            "Tambah Foto", fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    // Pratinjau gambar butuh library tambahan (Coil), jadi sementara tampil teks
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("1 foto dipilih", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { fotoUri = null }) {
                            Icon(Icons.Outlined.Close, contentDescription = "Hapus foto")
                        }
                    }
                }
            }

            // Dorong tombol ke bagian bawah layar
            Spacer(Modifier.weight(1f))

            SigapButton(
                text = "Simpan Perubahan",
                onClick = { onSimpan(status, catatan, fotoUri) }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun PerbaruiStatusPreview() {
    SIGAPTheme { PerbaruiStatusScreen() }
}