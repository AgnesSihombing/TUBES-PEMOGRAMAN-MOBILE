package com.example.sigap.ui.screens.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

private val TextDark = Color(0xFF1F2937)
private val BorderGray = Color(0xFFE5E7EB)

private val gedungList = listOf("Gedung A", "Gedung B", "Gedung C")
private val lantaiList = listOf("Lantai 1", "Lantai 2", "Lantai 3", "Lantai 4")
private val ruanganList = listOf("Ruang A201", "Ruang A202", "Ruang A203")

@Composable
fun ReportLocationScreen(navController: NavHostController) {
    var gedung by rememberSaveable { mutableStateOf(gedungList.first()) }
    var lantai by rememberSaveable { mutableStateOf(lantaiList[1]) }
    var ruangan by rememberSaveable { mutableStateOf(ruanganList.first()) }
    var keterangan by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = TextDark,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )
                Text(
                    text = "Buat Laporan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 24.dp)
                )
            }

            // Isi form (bisa di-scroll)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                StepIndicator(currentStep = 2)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = locationTitle(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                LocationDropdown(
                    label = "Gedung",
                    selected = gedung,
                    options = gedungList,
                    onSelected = { gedung = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                LocationDropdown(
                    label = "Lantai",
                    selected = lantai,
                    options = lantaiList,
                    onSelected = { lantai = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                LocationDropdown(
                    label = "Ruangan",
                    selected = ruangan,
                    options = ruanganList,
                    onSelected = { ruangan = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Keterangan (Opsional)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = keterangan,
                    onValueChange = { keterangan = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Contoh: sisi kiri ruang, dekat jendela",
                            fontSize = 13.sp,
                            color = Color(0xFF9CA3AF)
                        )
                    },
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SigapPrimary,
                        unfocusedBorderColor = BorderGray,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Tombol bawah
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.5.dp, SigapPrimary)
                ) {
                    Text(
                        text = "Kembali",
                        color = SigapPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = {
                        navController.navigate("confirm_report")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SigapPrimary)
                ) {
                    Text(
                        text = "Lanjut",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

/** Komponen Indikator Tahapan (Stepper) */
@Composable
private fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StepItem(stepNumber = "1", label = "Data", isCompleted = currentStep > 1, isActive = currentStep == 1)
        StepLine(isActive = currentStep >= 2)
        StepItem(stepNumber = "2", label = "Lokasi", isCompleted = currentStep > 2, isActive = currentStep == 2)
        StepLine(isActive = currentStep >= 3)
        StepItem(stepNumber = "3", label = "Konfirmasi", isCompleted = currentStep > 3, isActive = currentStep == 3)
    }
}

@Composable
private fun StepItem(stepNumber: String, label: String, isCompleted: Boolean, isActive: Boolean) {
    val bgColor = if (isActive || isCompleted) SigapPrimary else Color(0xFFE5E7EB)
    val textColor = if (isActive || isCompleted) Color.White else Color(0xFF6B7280)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isActive) SigapPrimary else Color(0xFF6B7280),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun StepLine(isActive: Boolean) {
    Spacer(
        modifier = Modifier
            .width(40.dp)
            .height(2.dp)
            .background(if (isActive) SigapPrimary else Color(0xFFE5E7EB))
    )
}

/** Judul "Lokasi *" dengan tanda bintang merah */
private fun locationTitle(): AnnotatedString = buildAnnotatedString {
    append("Lokasi ")
    withStyle(SpanStyle(color = Color(0xFFEF4444))) {
        append("*")
    }
}

@Composable
private fun LocationDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selected,
                    fontSize = 14.sp,
                    color = TextDark
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextDark
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.White
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 14.sp, color = TextDark) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun ReportLocationScreenPreview() {
    SIGAPTheme {
        ReportLocationScreen(navController = rememberNavController())
    }
}