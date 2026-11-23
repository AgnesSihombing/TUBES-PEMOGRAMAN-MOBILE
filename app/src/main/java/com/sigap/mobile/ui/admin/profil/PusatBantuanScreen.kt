package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
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

private val daftarFaq = listOf(
    "Bagaimana memverifikasi laporan?" to
            "Buka tab Laporan, pilih laporan berstatus Menunggu Verifikasi, tentukan prioritas, lalu tekan Verifikasi & Tugaskan Teknis.",
    "Kapan sebuah laporan ditolak?" to
            "Laporan ditolak jika tidak dapat ditindaklanjuti, misalnya bukan kerusakan fasilitas. Sertakan alasan yang jelas.",
    "Bagaimana memperbarui progres perbaikan?" to
            "Buka laporan yang sedang diproses, ubah status, tulis catatan progres, dan tambahkan foto bukti jika ada.",
    "Bagaimana menambah pengguna baru?" to
            "Buka tab Pengguna, tekan Tambah, lengkapi identitas dan peran. Tautan buat kata sandi dikirim lewat email."
)

@Composable
fun PusatBantuanScreen(
    onBack: () -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    // Menyimpan nomor pertanyaan yang sedang terbuka (null = semua tertutup)
    var terbuka by remember { mutableStateOf<Int?>(null) }

    SigapSubScreen("Pusat Bantuan", onBack, onTabSelected) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            daftarFaq.forEachIndexed { index, (tanya, jawab) ->
                val buka = terbuka == index
                SigapCard(modifier = Modifier.clickable { terbuka = if (buka) null else index }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(tanya, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                        Icon(
                            if (buka) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                    if (buka) {
                        Spacer(Modifier.height(8.dp))
                        Text(jawab, fontSize = 12.sp, color = SigapTextSecondary)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun PusatBantuanPreview() {
    SIGAPTheme { PusatBantuanScreen() }
}