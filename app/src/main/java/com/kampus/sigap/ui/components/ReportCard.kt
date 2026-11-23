package com.kampus.sigap.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.model.Report
import com.kampus.sigap.ui.theme.*

@Composable
fun ReportCard(report: Report, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(16.dp)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClick),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = SigapSurface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("#${report.id}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SigapText)
                Text(report.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = SigapText)
                Text(report.shortLocation, fontSize = 14.sp, color = SigapTextMuted)
            }
            Spacer(Modifier.width(8.dp))
            StatusChip(report.status, Modifier.widthIn(max = 110.dp))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = SigapText)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportCardPreview() {
    SigapTheme {
        Column(Modifier.padding(16.dp)) {
            ReportCard(DummyData.reports.first(), {})
        }
    }
}