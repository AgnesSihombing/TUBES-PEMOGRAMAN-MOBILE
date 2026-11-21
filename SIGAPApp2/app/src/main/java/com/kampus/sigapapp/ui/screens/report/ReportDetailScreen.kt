package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.ReportStatus
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.Chip
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.OutlineButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.StatusChip
import com.kampus.sigapapp.ui.theme.*

@Composable
fun ReportDetailScreen(
    reportId: String,
    onBack: () -> Unit,
    onFeedback: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val report = DummyData.findReport(reportId)

    MainScaffold(selected = Routes.MY_REPORTS, onNavigate = onNavigate) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(onBack = onBack, title = "Detail Laporan")
            Column(Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "#${report.id}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    StatusChip(report.status)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = report.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = BrandPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = report.locationFull, fontSize = 15.sp, color = TextDark)
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            tint = PrimaryDark,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Kategori",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(text = report.category, fontSize = 15.sp, color = TextDark)
                        }
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.MyLocation,
                                contentDescription = null,
                                tint = TextDark,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Prioritas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Chip(
                            text = report.priority,
                            background = ChipWaitingBg,
                            contentColor = ChipWaitingText
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Deskripsi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(Modifier.height(8.dp))
                Text(text = report.description, fontSize = 15.sp, color = TextDark)
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Foto",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Image(
                        painter = painterResource(R.drawable.img_report_ac_1),
                        contentDescription = "Foto kerusakan 1",
                        modifier = Modifier
                            .size(width = 140.dp, height = 100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(R.drawable.img_report_ac_2),
                        contentDescription = "Foto kerusakan 2",
                        modifier = Modifier
                            .size(width = 140.dp, height = 100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                if (report.status == ReportStatus.DONE) {
                    Spacer(Modifier.height(28.dp))
                    OutlineButton(
                        text = "Beri Feedback",
                        onClick = onFeedback,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}