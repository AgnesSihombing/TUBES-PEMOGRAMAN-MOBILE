package com.sigap.mobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Daftar tab admin. Enum dipakai agar nama tab tidak salah ketik.
enum class AdminTab(val label: String, val icon: ImageVector) {
    Beranda("Beranda", Icons.Outlined.Home),
    Laporan("Laporan", Icons.Outlined.Description),
    Pengguna("Pengguna", Icons.Outlined.Group),
    Profil("Profil", Icons.Outlined.Person)
}

// Bottom bar yang sama dipakai di semua layar admin
@Composable
fun AdminBottomBar(
    selected: AdminTab,
    onSelect: (AdminTab) -> Unit
) {
    NavigationBar(containerColor = Color.White) {
        AdminTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = tab == selected,
                onClick = { onSelect(tab) },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}