package com.sigap.mobile.ui.admin.pengguna

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Pengguna
import com.sigap.mobile.data.model.Peran
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.PenggunaAvatar
import com.sigap.mobile.ui.components.PeranChip
import com.sigap.mobile.ui.components.SigapTextField
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

// Data dummy (public agar layar detail bisa mencari berdasarkan ID). Nanti dari Firebase.
val dummyPengguna = listOf(
    Pengguna("241402157", "Martin Simanjuntak", "martin@students.usu.ac.id", Peran.Mahasiswa, true, "5 Agustus 2026", "10 November 2026, 09.15"),
    Pengguna("211402158", "Aulia Putri", "aulia@students.usu.ac.id", Peran.Mahasiswa, false, "5 Agustus 2026", "1 Oktober 2026, 14.20"),
    Pengguna("198705122015041002", "Dwi Prasetyo", "dwi.prasetyo@usu.ac.id", Peran.Dosen, true, "10 Agustus 2026", "9 November 2026, 11.05"),
    Pengguna("200105122015041001", "Andi Setiawan", "andi.setiawan@usu.ac.id", Peran.Staff, true, "10 Agustus 2026", "8 November 2026, 16.40"),
    Pengguna("ADM-001", "Rina Wulandari", "rina.wulandari@kampus.ac.id", Peran.Admin, true, "12 Agustus 2026", "8 November 2026, 08.30")
)

@Composable
fun PenggunaListScreen(
    onTabSelected: (AdminTab) -> Unit = {},
    onTambahClick: () -> Unit = {},
    onPenggunaClick: (Pengguna) -> Unit = {}
) {
    // State: teks pencarian dan filter peran (null = Semua)
    var query by remember { mutableStateOf("") }
    var peranDipilih by remember { mutableStateOf<Peran?>(null) }

    // Tampilkan data yang lolos filter peran DAN cocok dengan pencarian
    val filtered = dummyPengguna.filter {
        (peranDipilih == null || it.peran == peranDipilih) &&
                (query.isBlank() ||
                        it.nama.contains(query, ignoreCase = true) ||
                        it.id.contains(query, ignoreCase = true))
    }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Kelola Pengguna", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onTambahClick,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Outlined.Add, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Tambah", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                SigapTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = "Cari nama, NIM, atau NIP...",
                    leadingIcon = Icons.Outlined.Search,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Outlined.FilterList, "Filter", tint = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipItem("Semua", peranDipilih == null) { peranDipilih = null }
                Peran.entries.forEach { p ->
                    FilterChipItem(p.label, peranDipilih == p) { peranDipilih = p }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (filtered.isEmpty()) {
                Text("Tidak ada pengguna ditemukan", color = SigapTextSecondary, fontSize = 13.sp)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filtered, key = { it.id }) { p ->
                        PenggunaItem(p, onClick = { onPenggunaClick(p) })
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChipItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
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
            .padding(horizontal = 14.dp, vertical = 7.dp)
    )
}

@Composable
private fun PenggunaItem(pengguna: Pengguna, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PenggunaAvatar(pengguna.nama)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(pengguna.nama, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(
                "${pengguna.peran.idLabel} ${pengguna.id}",
                fontSize = 11.sp, color = SigapTextSecondary
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                PeranChip(pengguna.peran, pengguna.aktif)
                Spacer(Modifier.width(8.dp))
                Text(
                    if (pengguna.aktif) "Aktif" else "Non-Aktif",
                    fontSize = 11.sp, color = SigapTextSecondary
                )
            }
        }
        Icon(Icons.Outlined.ChevronRight, null, tint = SigapTextSecondary)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun PenggunaListPreview() {
    SIGAPTheme { PenggunaListScreen() }
}