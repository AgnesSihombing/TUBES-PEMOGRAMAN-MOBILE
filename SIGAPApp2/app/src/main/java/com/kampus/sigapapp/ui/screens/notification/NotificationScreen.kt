package com.kampus.sigapapp.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.data.AppNotification
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.NotificationType
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.components.FilterChips
import com.kampus.sigapapp.ui.components.MainScaffold
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.theme.*

@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    onReportClick: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    val filters = listOf("Semua", "Laporan", "Sistem")
    var filter by rememberSaveable { mutableStateOf("Semua") }
    val items = DummyData.notifications.filter {
        when (filter) {
            "Laporan" -> it.type == NotificationType.REPORT
            "Sistem" -> it.type == NotificationType.SYSTEM
            else -> true
        }
    }

    MainScaffold(selected = Routes.NOTIFICATIONS, onNavigate = onNavigate) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ScreenHeader(onBack = onBack, title = "Notifikasi")
            FilterChips(
                options = filters,
                selected = filter,
                onSelected = { filter = it },
                fill = true
            )
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClick = { notification.reportId?.let(onReportClick) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(notification: AppNotification, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppSurface)
            .border(1.dp, LineColor.copy(alpha = 0.6f), shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PrimarySoft),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = notification.icon,
                contentDescription = null,
                tint = PrimaryDark,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = notification.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = notification.message,
                fontSize = 15.sp,
                color = TextGray,
                lineHeight = 22.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(text = notification.time, fontSize = 13.sp, color = TextGray)
        }
    }
}