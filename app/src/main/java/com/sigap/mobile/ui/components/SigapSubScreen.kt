package com.sigap.mobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Kerangka layar turunan (judul + tombol kembali + bottom bar), dipakai ulang
@Composable
fun SigapSubScreen(
    title: String,
    onBack: () -> Unit,
    onTabSelected: (AdminTab) -> Unit,
    selectedTab: AdminTab = AdminTab.Profil,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { AdminBottomBar(selected = selectedTab, onSelect = onTabSelected) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Kembali")
                }
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}