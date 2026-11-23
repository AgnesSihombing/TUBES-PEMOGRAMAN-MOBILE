package com.kampus.sigap.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

private val termsSections = listOf(
    "1. Penggunaan Aplikasi" to "SIGAP digunakan oleh sivitas akademika untuk melaporkan gangguan atau kerusakan sarana dan prasarana di lingkungan kampus.",
    "2. Akun Pengguna" to "Pengguna wajib mendaftar menggunakan email kampus dan menjaga kerahasiaan kata sandi akunnya.",
    "3. Isi Laporan" to "Laporan harus benar, jelas, dan disertai foto yang sesuai. Laporan yang tidak sesuai dapat ditolak oleh admin.",
    "4. Penyalahgunaan" to "Pengguna dilarang membuat laporan palsu atau mengirim konten yang tidak pantas melalui aplikasi.",
    "5. Perubahan Ketentuan" to "Syarat dan ketentuan dapat diperbarui sewaktu-waktu. Pengguna disarankan memeriksanya secara berkala."
)

private val privacySections = listOf(
    "1. Data yang Dikumpulkan" to "SIGAP mengumpulkan nama, jenis pengguna, NIM/NIP/ID pegawai, email kampus, serta isi laporan yang meliputi foto dan lokasi kerusakan.",
    "2. Penggunaan Data" to "Data digunakan untuk memproses laporan, mengirim notifikasi perkembangan, dan meningkatkan kualitas layanan.",
    "3. Foto dan Kamera" to "Kamera hanya digunakan untuk mengambil foto kerusakan yang kamu laporkan.",
    "4. Keamanan Data" to "Kami berupaya menjaga keamanan data pengguna dan hanya membagikannya kepada pihak yang berwenang menangani laporan."
)

@Composable
fun TermsScreen(onBack: () -> Unit) {
    LegalContent("Syarat & Ketentuan", termsSections, onBack)
}

@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    LegalContent("Kebijakan Privasi", privacySections, onBack)
}

@Composable
private fun LegalContent(title: String, sections: List<Pair<String, String>>, onBack: () -> Unit) {
    Scaffold(containerColor = SigapBackground, topBar = { SigapTopBar(title, onBack) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            sections.forEach { (heading, body) ->
                Column {
                    Text(heading, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = SigapText)
                    Spacer(Modifier.height(6.dp))
                    Text(body, fontSize = 15.sp, color = SigapTextMuted, lineHeight = 23.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TermsScreenPreview() {
    SigapTheme { TermsScreen({}) }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrivacyScreenPreview() {
    SigapTheme { PrivacyScreen({}) }
}