package com.kampus.sigap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.ui.theme.*

@Composable
fun SigapTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    centerTitle: Boolean = false
) {
    Box(
        modifier
            .fillMaxWidth()
            .background(SigapBackground)
            .statusBarsPadding()
            .height(64.dp)
            .padding(horizontal = 8.dp)
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = SigapText)
        }
        Text(
            text = title,
            color = SigapText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(if (centerTitle) Alignment.Center else Alignment.CenterStart)
                .padding(start = if (centerTitle) 0.dp else 52.dp)
        )
    }
}

@Composable
fun SigapAuthTopBar(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .background(SigapBackground)
            .statusBarsPadding()
            .height(64.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = SigapText)
        }
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.logo_sigap_hijau),
            contentDescription = "Logo SIGAP",
            modifier = Modifier.height(28.dp).padding(end = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SigapTopBarPreview() {
    SigapTheme {
        Column {
            SigapTopBar("Laporan Saya", {})
            SigapTopBar("Buat Laporan", {}, centerTitle = true)
            SigapAuthTopBar({})
        }
    }
}