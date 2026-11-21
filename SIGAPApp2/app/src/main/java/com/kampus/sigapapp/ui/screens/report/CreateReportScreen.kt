package com.kampus.sigapapp.ui.screens.report

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.ui.components.Chip
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.OutlineButton
import com.kampus.sigapapp.ui.components.RequiredLabel
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapDropdown
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.components.StepIndicator
import com.kampus.sigapapp.ui.theme.*

@Composable
fun CreateReportScreen(
    photoTaken: Boolean,
    onAddPhoto: () -> Unit,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var step by rememberSaveable { mutableIntStateOf(1) }
    var category by rememberSaveable { mutableStateOf("AC") }
    var title by rememberSaveable { mutableStateOf("") }
    var building by rememberSaveable { mutableStateOf("Gedung A") }
    var floor by rememberSaveable { mutableStateOf("Lantai 2") }
    var room by rememberSaveable { mutableStateOf("Ruang A201") }
    var note by rememberSaveable { mutableStateOf("") }

    BackHandler(enabled = step > 1) { step -= 1 }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        ScreenHeader(
            onBack = { if (step > 1) step -= 1 else onBack() },
            title = "Buat Laporan",
            centerTitle = true
        )
        StepIndicator(current = step)
        Spacer(Modifier.height(20.dp))
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            when (step) {
                1 -> {
                    RequiredLabel("Foto Kerusakan")
                    Spacer(Modifier.height(12.dp))
                    PhotoPicker(photoTaken = photoTaken, onClick = onAddPhoto)
                    Spacer(Modifier.height(20.dp))
                    RequiredLabel("Kategori")
                    Spacer(Modifier.height(8.dp))
                    SigapDropdown(
                        value = category,
                        options = DummyData.categories,
                        onSelected = { category = it }
                    )
                    Spacer(Modifier.height(20.dp))
                    RequiredLabel("Judul Laporan")
                    Spacer(Modifier.height(8.dp))
                    SigapTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = "Contoh: AC tidak dingin"
                    )
                }
                2 -> {
                    RequiredLabel("Lokasi")
                    Spacer(Modifier.height(12.dp))
                    FieldLabel("Gedung")
                    SigapDropdown(
                        value = building,
                        options = DummyData.buildings,
                        onSelected = { building = it }
                    )
                    Spacer(Modifier.height(16.dp))
                    FieldLabel("Lantai")
                    SigapDropdown(
                        value = floor,
                        options = DummyData.floors,
                        onSelected = { floor = it }
                    )
                    Spacer(Modifier.height(16.dp))
                    FieldLabel("Ruangan")
                    SigapDropdown(
                        value = room,
                        options = DummyData.rooms,
                        onSelected = { room = it }
                    )
                    Spacer(Modifier.height(16.dp))
                    FieldLabel("Keterangan (Opsional)")
                    SigapTextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = "Contoh: sisi kiri ruang, dekat jendela"
                    )
                }
                else -> {
                    Text(
                        text = "Konfirmasi Laporan",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(Modifier.height(12.dp))
                    val cardShape = RoundedCornerShape(16.dp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(cardShape)
                            .background(AppSurface)
                            .border(1.dp, LineColor, cardShape)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        ConfirmRow(Icons.Outlined.Settings, "Kategori", category)
                        ConfirmRow(Icons.Outlined.Settings, "Judul", title.ifBlank { "-" })
                        ConfirmRow(
                            icon = Icons.Outlined.LocationOn,
                            label = "Lokasi",
                            value = "$building, $floor, $room",
                            extra = if (note.isNotBlank()) "Keterangan: $note" else null
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Flag,
                                contentDescription = null,
                                tint = PrimaryDark,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Text(text = "Prioritas", fontSize = 17.sp, color = TextDark)
                            Spacer(Modifier.width(12.dp))
                            Chip(
                                text = "Sedang",
                                background = ChipWaitingBg,
                                contentColor = ChipWaitingText
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (step > 1) {
                OutlineButton(
                    text = "Kembali",
                    onClick = { step -= 1 },
                    modifier = Modifier.weight(1f)
                )
            }
            GradientButton(
                text = if (step == 3) "Kirim Laporan" else "Lanjut",
                onClick = { if (step == 3) onSubmit() else step += 1 },
                modifier = Modifier.weight(1f),
                enabled = step != 1 || title.isNotBlank()
            )
        }
    }
}

@Composable
private fun PhotoPicker(photoTaken: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape)
            .background(FieldBg)
            .border(1.dp, LineColor, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (photoTaken) {
            Image(
                painter = painterResource(R.drawable.img_camera_preview),
                contentDescription = "Foto kerusakan",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.PhotoCamera,
                    contentDescription = null,
                    tint = PrimaryDark,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Tambah Foto",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "(Pilih dari galeri atau ambil foto)",
                    fontSize = 13.sp,
                    color = TextGray
                )
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextDark
    )
}

@Composable
private fun ConfirmRow(icon: ImageVector, label: String, value: String, extra: String? = null) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryDark,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 14.sp, color = TextGray)
            Text(text = value, fontSize = 17.sp, color = TextDark)
            if (extra != null) {
                Text(text = extra, fontSize = 17.sp, color = TextDark)
            }
        }
    }
}