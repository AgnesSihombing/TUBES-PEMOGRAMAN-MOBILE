package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.*
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

@Composable
fun NotifikasiScreen(
    onBack: () -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // Nanti disimpan ke Firebase atau DataStore agar pilihan tidak hilang
    var laporanBaru by remember { mutableStateOf(true) }
    var statusBerubah by remember { mutableStateOf(true) }
    var lewatEmail by remember { mutableStateOf(false) }

    SigapSubScreen("Pengaturan Notifikasi", onBack, onTabSelected) {
        SigapCard {
            SwitchRow("Laporan baru masuk", "Beri tahu saat ada laporan dari mahasiswa", laporanBaru) { laporanBaru = it }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 8.dp))
            SwitchRow("Pembaruan status", "Beri tahu saat status laporan berubah", statusBerubah) { statusBerubah = it }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 8.dp))
            SwitchRow("Notifikasi lewat email", "Kirim ringkasan ke email kampus", lewatEmail) { lewatEmail = it }
        }
    }
}

@Composable
private fun SwitchRow(judul: String, keterangan: String, aktif: Boolean, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(judul, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(keterangan, fontSize = 11.sp, color = SigapTextSecondary)
        }
        Switch(checked = aktif, onCheckedChange = onChange)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun NotifikasiPreview() {
    SIGAPTheme { NotifikasiScreen() }
}