package com.kampus.sigap.ui.components


import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.navigation.Routes
import com.kampus.sigap.ui.theme.*

enum class BottomTab(val label: String, val route: String, @DrawableRes val icon: Int) {
    Home("Beranda", Routes.Home, R.drawable.ic_nav_home),
    Reports("Laporan", Routes.MyReports, R.drawable.ic_nav_report),
    Notifications("Notifikasi", Routes.Notifications, R.drawable.ic_nav_notification),
    Profile("Profil", Routes.Profile, R.drawable.ic_nav_profile)
}

@Composable
fun SigapBottomBar(
    selected: BottomTab?,
    onTabClick: (BottomTab) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier, color = SigapSurface, shadowElevation = 16.dp) {
        Row(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(76.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(BottomTab.Home, selected, onTabClick, Modifier.weight(1f))
            BottomBarItem(BottomTab.Reports, selected, onTabClick, Modifier.weight(1f))
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .size(56.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(SigapGradientDiagonal)
                        .clickable(onClick = onAddClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, "Buat laporan", tint = Color.White, modifier = Modifier.size(30.dp))
                }
            }
            BottomBarItem(BottomTab.Notifications, selected, onTabClick, Modifier.weight(1f))
            BottomBarItem(BottomTab.Profile, selected, onTabClick, Modifier.weight(1f))
        }
    }
}

@Composable
private fun BottomBarItem(
    tab: BottomTab,
    selected: BottomTab?,
    onClick: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val active = tab == selected
    val tint by animateColorAsState(if (active) SigapPrimary else SigapText, label = "tabTint")
    val scale by animateFloatAsState(
        targetValue = if (active) 1.15f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "tabScale"
    )
    Column(
        modifier
            .fillMaxHeight()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick(tab) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(tab.icon),
            contentDescription = tab.label,
            tint = tint,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale }
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = tab.label,
            color = tint,
            fontSize = 11.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SigapBottomBarPreview() {
    SigapTheme { SigapBottomBar(BottomTab.Home, {}, {}) }
}