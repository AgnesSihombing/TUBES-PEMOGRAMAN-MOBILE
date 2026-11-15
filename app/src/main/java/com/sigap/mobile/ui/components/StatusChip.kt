package com.sigap.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.LaporanStatus
import com.sigap.mobile.ui.theme.*

// Chip berwarna sesuai status. Warna diambil dari Color.kt.
@Composable
fun StatusChip(status: LaporanStatus, modifier: Modifier = Modifier) {
    val (bg, fg) = when (status) {
        LaporanStatus.MenungguVerifikasi -> StatusMenungguBg to StatusMenungguFg
        LaporanStatus.Diproses -> StatusDiprosesBg to StatusDiprosesFg
        LaporanStatus.Selesai -> StatusSelesaiBg to StatusSelesaiFg
        LaporanStatus.Ditolak -> StatusDitolakBg to StatusDitolakFg
    }
    Text(
        text = status.label,
        color = fg,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}