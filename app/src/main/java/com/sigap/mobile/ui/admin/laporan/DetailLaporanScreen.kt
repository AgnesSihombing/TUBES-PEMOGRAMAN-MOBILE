package com.sigap.mobile.ui.admin.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Laporan
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.data.model.Prioritas
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.StatusChip
import com.sigap.mobile.ui.theme.*

// Data dummy. Nanti laporan dikirim dari layar daftar (lewat navigasi) atau diambil dari Firebase.
private val dummyDetail = Laporan(
    id = "#LP-20261108-001",
    judul = "AC tidak dingin",
    lokasi = "Gedung A, Lantai 2, Ruang A201",
    status = LaporanStatus.MenungguVerifikasi,
    kategori = "AC",
    deskripsi = "AC tidak mengeluarkan udara dingin sejak kemarin."
)

@Composable
fun DetailLaporanScreen(
    laporan: Laporan = dummyDetail,
    onBack: () -> Unit = {},
    onTolak: () -> Unit = {},
    onVerifikasi: (Prioritas) -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // State prioritas yang dipilih admin (default Sedang sesuai desain)
    var prioritas by remember { mutableStateOf(Prioritas.Sedang) }
    var menuTerbuka by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Laporan, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Baris judul dengan tombol kembali
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Kembali")
                }
                Text("Detail Laporan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            // ID laporan + chip status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(laporan.id, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                StatusChip(laporan.status)
            }

            Spacer(Modifier.height(16.dp))
            Text(laporan.judul, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.LocationOn, null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(laporan.lokasi, fontSize = 13.sp)
            }

            Spacer(Modifier.height(16.dp))

            Row {
                Icon(
                    Icons.Outlined.Settings, null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Kategori", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(laporan.kategori, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Deskripsi", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(laporan.deskripsi, fontSize = 13.sp, color = SigapTextSecondary)

            Spacer(Modifier.height(20.dp))
            Text("Foto", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // Placeholder foto. Nanti diganti gambar asli dari Firebase Storage.
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 78.dp)
                            .background(Color(0xFFE6EBEE), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Image, null, tint = SigapTextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Dropdown prioritas: Box membungkus baris dan menu agar menu muncul tepat di bawahnya
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { menuTerbuka = true }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Flag, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Pilih Prioritas", fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Outlined.KeyboardArrowDown, null)
                }
                DropdownMenu(expanded = menuTerbuka, onDismissRequest = { menuTerbuka = false }) {
                    Prioritas.entries.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.label) },
                            onClick = {
                                prioritas = item
                                menuTerbuka = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(6.dp))
            PrioritasChip(prioritas)

            Spacer(Modifier.height(24.dp))

            // Tombol aksi: Tolak (outline merah) dan Verifikasi (hijau)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onTolak,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StatusDitolakFg),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusDitolakFg)
                ) {
                    Text("Tolak", fontWeight = FontWeight.SemiBold)
                }
                Button(
                    onClick = { onVerifikasi(prioritas) },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Verifikasi &\nTugaskan Teknis",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Warna chip prioritas: Rendah = hijau, Sedang = kuning, Tinggi = merah
@Composable
private fun PrioritasChip(prioritas: Prioritas) {
    val (bg, fg) = when (prioritas) {
        Prioritas.Rendah -> StatusSelesaiBg to StatusSelesaiFg
        Prioritas.Sedang -> StatusMenungguBg to StatusMenungguFg
        Prioritas.Tinggi -> StatusDitolakBg to StatusDitolakFg
    }
    Text(
        text = prioritas.label,
        color = fg,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun DetailLaporanPreview() {
    SIGAPTheme { DetailLaporanScreen() }
}