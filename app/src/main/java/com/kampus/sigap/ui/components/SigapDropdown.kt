package com.kampus.sigap.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.theme.*

@Composable
fun SigapDropdown(
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(12.dp)
    val arrow by animateFloatAsState(if (expanded) 180f else 0f, label = "arrow")

    Box(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape)
                .background(SigapFieldBg)
                .border(1.dp, if (expanded) SigapPrimary else SigapBorder, shape)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(leadingIcon, null, tint = SigapPrimary)
                Spacer(Modifier.width(12.dp))
            }
            Text(
                text = selected.ifEmpty { placeholder },
                color = if (selected.isEmpty()) SigapTextMuted else SigapText,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = SigapText,
                modifier = Modifier.rotate(arrow)
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SigapDropdownPreview() {
    SigapTheme {
        Column(Modifier.padding(16.dp)) {
            SigapDropdown("", listOf("Mahasiswa", "Dosen"), {}, "Jenis Pengguna", leadingIcon = Icons.Outlined.ManageAccounts)
        }
    }
}