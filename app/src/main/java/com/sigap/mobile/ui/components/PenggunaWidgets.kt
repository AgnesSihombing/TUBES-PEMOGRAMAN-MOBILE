package com.sigap.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sigap.mobile.data.model.Peran
import com.sigap.mobile.ui.theme.SigapTextSecondary

// Lingkaran berisi inisial nama (mis. "Rina Wulandari" -> "RW")
@Composable
fun PenggunaAvatar(nama: String, size: Dp = 40.dp) {
    val inisial = nama.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
    Box(
        modifier = Modifier
            .size(size)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            inisial,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value * 0.35f).sp
        )
    }
}

// Chip peran: hijau jika akun aktif, abu-abu jika non-aktif
@Composable
fun PeranChip(peran: Peran, aktif: Boolean = true) {
    val bg = if (aktif) MaterialTheme.colorScheme.primaryContainer else Color(0xFFE6EBEE)
    val fg = if (aktif) MaterialTheme.colorScheme.primary else SigapTextSecondary
    Text(
        text = peran.label,
        color = fg,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}