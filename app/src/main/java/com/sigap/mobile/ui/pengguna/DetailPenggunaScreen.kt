package com.sigap.mobile.ui.admin.pengguna

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Pengguna
import com.sigap.mobile.ui.components.AdminBottomBar
import com.sigap.mobile.ui.components.AdminTab
import com.sigap.mobile.ui.components.PenggunaAvatar
import com.sigap.mobile.ui.components.PeranChip
import com.sigap.mobile.ui.components.SigapButton
import com.sigap.mobile.ui.components.SigapCard
import com.sigap.mobile.ui.theme.SIGAPTheme
import com.sigap.mobile.ui.theme.SigapTextSecondary
import com.sigap.mobile.ui.theme.StatusDitolakFg

@Composable
fun DetailPenggunaScreen(
    pengguna: Pengguna = dummyPengguna.last(),
    onBack: () -> Unit = {},
    onUbah: () -> Unit = {},
    onResetSandi: () -> Unit = {},
    onToggleAktif: () -> Unit = {},
    onTabSelected: (AdminTab) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = AdminTab.Pengguna, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Kembali")
                }
                Text("Detail Pengguna", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // Kartu profil singkat
            SigapCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PenggunaAvatar(pengguna.nama, size = 56.dp)
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(pengguna.nama, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "${pengguna.peran.idLabel} ${pengguna.id}",
                            fontSize = 11.sp, color = SigapTextSecondary
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PeranChip(pengguna.peran, pengguna.aktif)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                if (pengguna.aktif) "• Aktif" else "• Non-Aktif",
                                fontSize = 11.sp, color = SigapTextSecondary
                            )
                        }
                    }
                }
            }

            SigapCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Badge, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Identitas kampus", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(12.dp))
                InfoRow("ID pengguna", pengguna.id)
                InfoRow("Email kampus", pengguna.email)
            }

            SigapCard {
                Text("Informasi akun", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))
                InfoRow("Peran", pengguna.peran.label)
                InfoRow("Status akun", if (pengguna.aktif) "Aktif" else "Non-Aktif")
                InfoRow("Bergabung", pengguna.bergabung)
                InfoRow("Terakhir masuk", pengguna.terakhirMasuk)
            }

            SigapCard {
                Text("Kelola akun", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                AksiRow(Icons.Outlined.VpnKey, "Atur ulang kata sandi", onClick = onResetSandi)
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                AksiRow(
                    Icons.Outlined.PersonOff,
                    if (pengguna.aktif) "Nonaktifkan akun" else "Aktifkan akun",
                    merah = pengguna.aktif,
                    onClick = onToggleAktif
                )
            }

            SigapButton(text = "Ubah Pengguna", onClick = onUbah)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Text(label, fontSize = 12.sp, color = SigapTextSecondary, modifier = Modifier.weight(1f))
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1.4f))
    }
}

@Composable
private fun AksiRow(icon: ImageVector, teks: String, merah: Boolean = false, onClick: () -> Unit) {
    val warna = if (merah) StatusDitolakFg else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = warna, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(10.dp))
        Text(teks, fontSize = 13.sp, color = warna, modifier = Modifier.weight(1f))
        Icon(Icons.Outlined.ChevronRight, null, tint = SigapTextSecondary)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun DetailPenggunaPreview() {
    SIGAPTheme { DetailPenggunaScreen() }
}