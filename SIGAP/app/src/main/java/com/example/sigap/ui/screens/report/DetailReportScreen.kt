package com.example.sigap.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.R
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

// ---------- Warna lokal (samakan dengan Figma) ----------
private val TextPrimary = Color(0xFF1F2937)
private val TextSecondary = Color(0xFF4B5563)
private val TextMuted = Color(0xFF6B7280)
private val TextHint = Color(0xFF9CA3AF)
private val DividerColor = Color(0xFFE5E7EB)

private val StatusBg = Color(0xFFFEF3C7)
private val StatusText = Color(0xFFD97706)
private val PriorityBg = Color(0xFFFFEDD5)
private val PriorityText = Color(0xFFC2410C)

// ---------- Model data (nanti bisa diganti dari ViewModel/API) ----------
private data class DetailReport(
    val id: String,
    val status: String,
    val title: String,
    val location: String,
    val category: String,
    val priority: String,
    val description: String,
    val photos: List<Int>
)

private val sampleReport = DetailReport(
    id = "#LP-20261108-001",
    status = "Menunggu Verifikasi",
    title = "AC tidak dingin",
    location = "Gedung A, Lantai 2, Ruang A201",
    category = "AC",
    priority = "Sedang",
    description = "AC tidak mengeluarkan udara dingin sejak kemarin.",
    photos = listOf(R.drawable.img_ac_rusak1, R.drawable.img_ac_rusak2)
)

// ---------- Screen ----------
@Composable
fun DetailReportScreen(navController: NavHostController) {
    val report = sampleReport
    Scaffold(
        containerColor = Color.White,
        // Status bar asli HP dipakai, jadi tidak perlu status bar palsu "9:41"
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            SigapBottomBar(
                onHome = { navController.navigate("home") },
                onReport = { navController.navigate("report_history") },
                onAdd = { navController.navigate("create_report") },
                onNotification = { navController.navigate("notification") },
                onProfile = { navController.navigate("profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            DetailTopBar(onBack = { navController.popBackStack() })

            Spacer(Modifier.height(16.dp))

            // ID + badge status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = report.id,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )
                Chip(
                    text = report.status,
                    background = StatusBg,
                    contentColor = StatusText,
                    fontSize = 10.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = report.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = SigapPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(text = report.location, fontSize = 12.sp, color = TextSecondary)
            }

            Spacer(Modifier.height(14.dp))

            // Kategori (kiri) & Prioritas (kanan)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        tint = SigapPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Kategori",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(text = report.category, fontSize = 11.sp, color = TextSecondary)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(TextMuted)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Prioritas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Chip(
                        text = report.priority,
                        background = PriorityBg,
                        contentColor = PriorityText,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            SectionTitle("Deskripsi")
            Spacer(Modifier.height(4.dp))
            Text(
                text = report.description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = TextSecondary
            )

            Spacer(Modifier.height(20.dp))

            SectionTitle("Foto")
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                report.photos.forEachIndexed { index, res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "Foto laporan ${index + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.4f)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ---------- Komponen kecil ----------
@Composable
private fun DetailTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = TextPrimary,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Detail Laporan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )
}

@Composable
private fun Chip(
    text: String,
    background: Color,
    contentColor: Color,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    Surface(color = background, shape = RoundedCornerShape(50)) {
        Text(
            text = text,
            color = contentColor,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

// ---------- Bottom bar ----------
@Composable
private fun SigapBottomBar(
    onHome: () -> Unit,
    onReport: () -> Unit,
    onAdd: () -> Unit,
    onNotification: () -> Unit,
    onProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = DividerColor, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                label = "Beranda",
                selected = false,
                modifier = Modifier.weight(1f),
                onClick = onHome
            ) { tint ->
                Icon(Icons.Outlined.Home, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
            }
            NavItem(
                label = "Laporan",
                selected = true,
                modifier = Modifier.weight(1f),
                onClick = onReport
            ) { tint ->
                Icon(painterResource(R.drawable.ic_nav_report), contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(SigapPrimary)
                        .clickable(onClick = onAdd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Tambah laporan",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            NavItem(
                label = "Notifikasi",
                selected = false,
                modifier = Modifier.weight(1f),
                onClick = onNotification
            ) { tint ->
                Icon(painterResource(R.drawable.ic_nav_notification), contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
            }
            NavItem(
                label = "Profil",
                selected = false,
                modifier = Modifier.weight(1f),
                onClick = onProfile
            ) { tint ->
                Icon(painterResource(R.drawable.ic_nav_profile), contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (tint: Color) -> Unit
) {
    val tint = if (selected) SigapPrimary else TextHint
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon(tint)
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = tint,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailReportScreenPreview() {
    SIGAPTheme {
        DetailReportScreen(rememberNavController())
    }
}
