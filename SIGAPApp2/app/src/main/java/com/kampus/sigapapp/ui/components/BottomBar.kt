package com.kampus.sigapapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.navigation.Routes
import com.kampus.sigapapp.ui.theme.*

@Composable
fun MainScaffold(
    selected: String,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = { SigapBottomBar(selected = selected, onNavigate = onNavigate) }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}

@Composable
fun SigapBottomBar(selected: String, onNavigate: (String) -> Unit) {
    Column {
        HorizontalDivider(color = LineColor)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppSurface)
                .navigationBarsPadding()
                .height(72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomItem(
                label = "Beranda",
                icon = R.drawable.ic_nav_home,
                selected = selected == Routes.HOME,
                onClick = { onNavigate(Routes.HOME) },
                modifier = Modifier.weight(1f)
            )
            BottomItem(
                label = "Laporan",
                icon = R.drawable.ic_nav_report,
                selected = selected == Routes.MY_REPORTS,
                onClick = { onNavigate(Routes.MY_REPORTS) },
                modifier = Modifier.weight(1f)
            )
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .offset(y = (-6).dp)
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(GradientPrimary)
                        .clickable { onNavigate(Routes.CREATE_REPORT) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_nav_add),
                        contentDescription = "Buat laporan",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            BottomItem(
                label = "Notifikasi",
                icon = R.drawable.ic_nav_notification,
                selected = selected == Routes.NOTIFICATIONS,
                onClick = { onNavigate(Routes.NOTIFICATIONS) },
                modifier = Modifier.weight(1f)
            )
            BottomItem(
                label = "Profil",
                icon = R.drawable.ic_nav_profile,
                selected = selected == Routes.PROFILE,
                onClick = { onNavigate(Routes.PROFILE) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomItem(
    label: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (selected) BrandPrimary else TextDark
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = color,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}