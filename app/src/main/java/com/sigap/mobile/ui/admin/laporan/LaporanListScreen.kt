package com.sigap.mobile.ui.admin.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Laporan
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.SigapTextField
import com.sigap.mobile.ui.components.StatusChip
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

// Data dummy. Nanti diganti data dari Firebase.
private val dummyLaporan = listOf(
    Laporan("#LP-20261108-001", "AC tidak dingin", "Gedung A, Ruang A201", LaporanStatus.MenungguVerifikasi),
    Laporan("#LP-20261105-004", "Lampu mati", "Gedung B, Ruang B103", LaporanStatus.Diproses),
    Laporan("#LP-20261103-007", "Ada kucing", "Gedung C, Ruang C301", LaporanStatus.Ditolak),
    Laporan("#LP-20261110-012", "Kursi rusak", "Gedung A, Ruang A104", LaporanStatus.MenungguVerifikasi),
    Laporan("#LP-20261111-015", "Air kran bocor", "Gedung B, Ruang B205", LaporanStatus.Diproses),
    Laporan("#LP-20261112-018", "Lift tidak berfungsi", "Gedung C, Lantai 2", LaporanStatus.Ditolak),
    Laporan("#LP-20261113-021", "Wi-Fi tidak stabil", "Perpustakaan, Lantai 1", LaporanStatus.MenungguVerifikasi),
    Laporan("#LP-20261114-024", "Proyektor mati", "Gedung A, Ruang A302", LaporanStatus.Diproses)
)

@Composable
fun LaporanListScreen(
    onTabSelected: (AdminTab) -> Unit = {},
    onLaporanClick: (Laporan) -> Unit = {}
) {
    // State: teks pencarian dan filter yang dipilih (null = Semua)
    var query by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<LaporanStatus?>(null) }

    // Daftar yang ditampilkan = data yang lolos filter status DAN cocok dengan pencarian
    val filtered = dummyLaporan.filter { laporan ->
        val cocokStatus = selectedStatus == null || laporan.status == selectedStatus
        val cocokCari = query.isBlank() ||
                laporan.id.contains(query, ignoreCase = true) ||
                laporan.judul.contains(query, ignoreCase = true) ||
                laporan.lokasi.contains(query, ignoreCase = true)
        cocokStatus && cocokCari
    }

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
            Text("Kelola Laporan", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.padding(top = 16.dp))

            // Kolom cari + ikon filter
            Row(verticalAlignment = Alignment.CenterVertically) {
                SigapTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = "Cari nomor laporan, judul, atau lokasi...",
                    leadingIcon = Icons.Outlined.Search,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Outlined.FilterList, "Filter", tint = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.padding(top = 12.dp))

            // Chip filter, bisa digeser ke samping
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipItem("Semua", selectedStatus == null) { selectedStatus = null }
                FilterChipItem("Menunggu", selectedStatus == LaporanStatus.MenungguVerifikasi) {
                    selectedStatus = LaporanStatus.MenungguVerifikasi
                }
                FilterChipItem("Diproses", selectedStatus == LaporanStatus.Diproses) {
                    selectedStatus = LaporanStatus.Diproses
                }
                FilterChipItem("Selesai", selectedStatus == LaporanStatus.Selesai) {
                    selectedStatus = LaporanStatus.Selesai
                }
                FilterChipItem("Ditolak", selectedStatus == LaporanStatus.Ditolak) {
                    selectedStatus = LaporanStatus.Ditolak
                }
            }

            Spacer(Modifier.padding(top = 12.dp))

            if (filtered.isEmpty()) {
                Text("Tidak ada laporan ditemukan", color = SigapTextSecondary, fontSize = 13.sp)
            } else {
                // LazyColumn hanya menggambar item yang terlihat, hemat untuk daftar panjang
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filtered, key = { it.id }) { laporan ->
                        LaporanItem(laporan, onClick = { onLaporanClick(laporan) })
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChipItem(label: String, selected: Boolean, onClick: () -> Unit) {
    val primary = MaterialTheme.colorScheme.primary
    Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = if (selected) Color.White else SigapTextSecondary,
        modifier = Modifier
            .background(
                if (selected) primary else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                RoundedCornerShape(50)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    )
}

@Composable
private fun LaporanItem(laporan: Laporan, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(laporan.id, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            Text(laporan.judul, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(laporan.lokasi, fontSize = 11.sp, color = SigapTextSecondary)
        }
        StatusChip(laporan.status)
        Icon(Icons.Outlined.ChevronRight, null, tint = SigapTextSecondary)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun LaporanListPreview() {
    SIGAPTheme { LaporanListScreen() }
}