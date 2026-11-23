package com.sigap.mobile.ui.admin.profil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.ui.components.*
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary

@Composable
fun TentangScreen(
    onBack: () -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    SigapSubScreen("Tentang Aplikasi", onBack, onTabSelected) {
        SigapCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "SIGAP", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Versi 1.0.0", fontSize = 12.sp, color = SigapTextSecondary)
                Spacer(Modifier.height(16.dp))
                Text(
                    "Sistem Informasi Gangguan dan Aduan Prasarana",
                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Aplikasi pengaduan fasilitas kampus agar pelaporan, pengelolaan, dan pemantauan kerusakan menjadi lebih terstruktur dan transparan.",
                    fontSize = 12.sp, color = SigapTextSecondary, textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun TentangPreview() {
    SIGAPTheme { TentangScreen() }
}