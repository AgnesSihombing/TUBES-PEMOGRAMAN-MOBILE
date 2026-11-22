package com.kampus.sigap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.ReportStatus
import com.kampus.sigap.ui.theme.SigapTheme

@Composable
fun StatusChip(status: ReportStatus, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(50))
            .background(status.background)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = status.label,
            color = status.foreground,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PriorityChip(priority: String, modifier: Modifier = Modifier) {
    val (background, foreground) = when (priority) {
        "Tinggi" -> Color(0xFFFDE2E3) to Color(0xFFE5484D)
        "Rendah" -> Color(0xFFDDF3E4) to Color(0xFF2E8B57)
        else -> Color(0xFFFFF0C9) to Color(0xFFE08A00)
    }
    Box(
        modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(priority, color = foreground, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusChipPreview() {
    SigapTheme {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ReportStatus.entries.forEach { StatusChip(it) }
            PriorityChip("Sedang")
        }
    }
}