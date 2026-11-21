package com.kampus.sigapapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.data.Report
import com.kampus.sigapapp.data.ReportStatus
import com.kampus.sigapapp.ui.theme.*

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (enabled) GradientPrimary else SolidColor(DisabledGray))
            .clickable(enabled = enabled, onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
        }
        Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .height(52.dp)
            .clip(shape)
            .border(1.dp, if (enabled) BrandPrimary else LineColor, shape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) PrimaryDark else TextGray,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SigapTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    onEndIconClick: () -> Unit = {},
    isPassword: Boolean = false,
    singleLine: Boolean = true,
    minHeight: Dp = 52.dp,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }
    val leading: (@Composable () -> Unit)? = if (leadingIcon != null) {
        { Icon(imageVector = leadingIcon, contentDescription = null, tint = PrimaryDark) }
    } else null
    val trailing: (@Composable () -> Unit)? = when {
        isPassword -> {
            {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = "Tampilkan kata sandi",
                        tint = TextDark
                    )
                }
            }
        }
        endIcon != null -> {
            {
                IconButton(onClick = onEndIconClick) {
                    Icon(imageVector = endIcon, contentDescription = null, tint = PrimaryDark)
                }
            }
        }
        else -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight),
        placeholder = { Text(text = placeholder, color = PlaceholderColor, fontSize = 15.sp) },
        leadingIcon = leading,
        trailingIcon = trailing,
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = singleLine,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrandPrimary,
            unfocusedBorderColor = LineColor,
            focusedContainerColor = FieldBg,
            unfocusedContainerColor = FieldBg,
            cursorColor = BrandPrimary,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark
        )
    )
}

@Composable
fun SigapDropdown(
    value: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(12.dp)
    Box(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(shape)
                .background(FieldBg)
                .border(1.dp, LineColor, shape)
                .clickable { expanded = true }
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null, tint = PrimaryDark)
                Spacer(Modifier.width(12.dp))
            }
            Text(
                text = if (value.isEmpty()) placeholder else value,
                modifier = Modifier.weight(1f),
                color = if (value.isEmpty()) PlaceholderColor else TextDark,
                fontSize = 15.sp
            )
            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null, tint = TextDark)
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

@Composable
fun RequiredLabel(text: String, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(text = " *", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DangerRed)
    }
}

@Composable
fun Chip(text: String, background: Color, contentColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .widthIn(max = 108.dp)
            .clip(RoundedCornerShape(50))
            .background(background)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
        )
    }
}

@Composable
fun StatusChip(status: ReportStatus, modifier: Modifier = Modifier) {
    val (background, content) = when (status) {
        ReportStatus.WAITING -> ChipWaitingBg to ChipWaitingText
        ReportStatus.PROCESS -> ChipProcessBg to ChipProcessText
        ReportStatus.DONE -> ChipDoneBg to ChipDoneText
        ReportStatus.REJECTED -> ChipRejectedBg to ChipRejectedText
    }
    Chip(text = status.label, background = background, contentColor = content, modifier = modifier)
}

@Composable
fun SigapLogo(white: Boolean, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(if (white) R.drawable.logo_sigap_white else R.drawable.logo_sigap_green),
        contentDescription = "Logo SIGAP",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ScreenHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    centerTitle: Boolean = false,
    showLogo: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = TextDark,
                modifier = Modifier
                    .size(26.dp)
                    .clickable(onClick = onBack)
            )
            if (title != null && !centerTitle) {
                Spacer(Modifier.width(16.dp))
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
        }
        if (title != null && centerTitle) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        if (showLogo) {
            SigapLogo(white = false, modifier = Modifier.align(Alignment.CenterEnd).width(96.dp))
        }
    }
}

@Composable
fun StepIndicator(current: Int, modifier: Modifier = Modifier) {
    val labels = listOf("Data", "Lokasi", "Konfirmasi")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.Top
    ) {
        labels.forEachIndexed { index, label ->
            val step = index + 1
            val active = step == current
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (active) BrandPrimary else StepInactive),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$step",
                        color = if (active) Color.White else TextGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = if (active) BrandPrimary else TextGray,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
                )
            }
            if (step < labels.size) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 19.dp)
                        .height(2.dp)
                        .background(StepLine)
                )
            }
        }
    }
}

@Composable
fun FilterChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    fill: Boolean = false
) {
    val rowModifier = if (fill) {
        modifier.fillMaxWidth().padding(horizontal = 20.dp)
    } else {
        modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 20.dp)
    }
    Row(modifier = rowModifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { option ->
            val isSelected = option == selected
            Box(
                modifier = (if (fill) Modifier.weight(1f) else Modifier)
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) BrandPrimary else ChipInactive)
                    .clickable { onSelected(option) }
                    .padding(horizontal = 20.dp, vertical = 9.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Color.White else TextDark
                )
            }
        }
    }
}

@Composable
fun ReportCard(report: Report, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppSurface)
            .border(1.dp, LineColor.copy(alpha = 0.6f), shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = "#${report.id}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(text = report.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(text = report.locationShort, fontSize = 14.sp, color = TextGray)
        }
        Spacer(Modifier.width(8.dp))
        StatusChip(report.status)
        Spacer(Modifier.width(6.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGray
        )
    }
}