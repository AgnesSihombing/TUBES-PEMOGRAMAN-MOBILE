package com.kampus.sigap.ui.screens.report

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.model.DummyData
import com.kampus.sigap.model.Report
import com.kampus.sigap.model.ReportStatus
import com.kampus.sigap.ui.components.*
import com.kampus.sigap.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateReportScreen(onBack: () -> Unit, onSubmitted: (String) -> Unit) {
    var step by rememberSaveable { mutableIntStateOf(0) }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var category by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var building by rememberSaveable { mutableStateOf("") }
    var floor by rememberSaveable { mutableStateOf("") }
    var room by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    BackHandler(enabled = step > 0) { step -= 1 }

    val canNext = when (step) {
        0 -> photoUri != null && category.isNotBlank() && title.isNotBlank()
        1 -> building.isNotBlank() && floor.isNotBlank() && room.isNotBlank()
        else -> true
    }
    val priority = DummyData.priorityFor(category)

    Scaffold(
        containerColor = SigapBackground,
        topBar = {
            SigapTopBar("Buat Laporan", onBack = { if (step > 0) step -= 1 else onBack() }, centerTitle = true)
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (step > 0) {
                    SigapOutlinedButton("Kembali", { step -= 1 }, Modifier.weight(1f))
                }
                SigapPrimaryButton(
                    text = if (step == 2) "Kirim Laporan" else "Lanjut",
                    onClick = {
                        if (step < 2) {
                            step += 1
                        } else {
                            val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                            val number = "LP-$date-" + (DummyData.reports.size + 1).toString().padStart(3, '0')
                            DummyData.addReport(
                                Report(
                                    id = number,
                                    title = title.trim(),
                                    building = building,
                                    floor = floor,
                                    room = room,
                                    category = category,
                                    priority = priority,
                                    description = note.ifBlank { "Tidak ada keterangan tambahan." },
                                    status = ReportStatus.Waiting,
                                    photoUris = listOfNotNull(photoUri)
                                )
                            )
                            onSubmitted(number)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = canNext
                )
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp)) {
            StepIndicator(step)
            Spacer(Modifier.height(20.dp))
            AnimatedContent(
                targetState = step,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                    }
                },
                label = "stepContent"
            ) { current ->
                when (current) {
                    0 -> StepData(
                        photoUri = photoUri,
                        onPhotoPicked = { photoUri = it },
                        category = category,
                        onCategory = { category = it },
                        title = title,
                        onTitle = { title = it }
                    )
                    1 -> StepLocation(
                        building = building,
                        floor = floor,
                        room = room,
                        note = note,
                        onBuilding = { building = it; room = "" },
                        onFloor = { floor = it; room = "" },
                        onRoom = { room = it },
                        onNote = { note = it }
                    )
                    else -> StepConfirm(category, title, building, floor, room, note, priority)
                }
            }
        }
    }
}

@Composable
private fun StepIndicator(current: Int) {
    val labels = listOf("Data", "Lokasi", "Konfirmasi")
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        labels.forEachIndexed { index, label ->
            Column(Modifier.width(76.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val done = index < current
                val active = index == current
                val circleColor by animateColorAsState(
                    when {
                        active -> SigapPrimary
                        done -> SigapSecondary
                        else -> Color(0xFFDDE6E8)
                    },
                    label = "circle"
                )
                Box(
                    Modifier.size(36.dp).clip(CircleShape).background(circleColor),
                    contentAlignment = Alignment.Center
                ) {
                    if (done) {
                        Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text(
                            "${index + 1}",
                            color = if (active) Color.White else SigapTextMuted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    label,
                    fontSize = 12.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
                    color = if (active) SigapPrimary else SigapTextMuted
                )
            }
            if (index < labels.lastIndex) {
                val lineColor by animateColorAsState(
                    if (index < current) SigapPrimary else SigapBorder,
                    label = "line"
                )
                Box(
                    Modifier
                        .weight(1f)
                        .padding(top = 17.dp)
                        .height(2.dp)
                        .background(lineColor)
                )
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String, required: Boolean = false) {
    Text(
        text = buildAnnotatedString {
            append(text)
            if (required) withStyle(SpanStyle(color = SigapError)) { append(" *") }
        },
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = SigapText
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun StepData(
    photoUri: Uri?,
    onPhotoPicked: (Uri) -> Unit,
    category: String,
    onCategory: (String) -> Unit,
    title: String,
    onTitle: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) onPhotoPicked(uri)
    }
    val bitmap = rememberUriBitmap(photoUri)
    val shape = RoundedCornerShape(16.dp)

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        FieldLabel("Foto Kerusakan", required = true)
        Box(
            Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(shape)
                .background(Color(0xFFEFF5F5))
                .border(1.dp, SigapBorder, shape)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(bitmap, "Foto kerusakan", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Text(
                    "Ketuk untuk mengganti",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x99000000))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.PhotoCamera, null, tint = SigapPrimaryDark, modifier = Modifier.size(40.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Tambah Foto", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SigapText)
                    Text("(Pilih dari galeri atau ambil foto)", fontSize = 13.sp, color = SigapTextMuted)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        FieldLabel("Kategori", required = true)
        SigapDropdown(category, DummyData.categories, onCategory, "Pilih kategori")
        Spacer(Modifier.height(20.dp))
        FieldLabel("Judul Laporan", required = true)
        SigapTextField(title, onTitle, "Contoh: AC tidak dingin")
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun StepLocation(
    building: String,
    floor: String,
    room: String,
    note: String,
    onBuilding: (String) -> Unit,
    onFloor: (String) -> Unit,
    onRoom: (String) -> Unit,
    onNote: (String) -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        FieldLabel("Lokasi", required = true)
        Text("Gedung", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SigapText)
        Spacer(Modifier.height(6.dp))
        SigapDropdown(building, DummyData.buildings, onBuilding, "Pilih gedung")
        Spacer(Modifier.height(14.dp))
        Text("Lantai", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SigapText)
        Spacer(Modifier.height(6.dp))
        SigapDropdown(floor, DummyData.floors, onFloor, "Pilih lantai")
        Spacer(Modifier.height(14.dp))
        Text("Ruangan", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SigapText)
        Spacer(Modifier.height(6.dp))
        SigapDropdown(room, DummyData.roomsFor(building, floor), onRoom, "Pilih ruangan")
        Spacer(Modifier.height(14.dp))
        Text("Keterangan (Opsional)", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SigapText)
        Spacer(Modifier.height(6.dp))
        SigapTextField(note, onNote, "Contoh: sisi kiri ruang, dekat jendela")
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun StepConfirm(
    category: String,
    title: String,
    building: String,
    floor: String,
    room: String,
    note: String,
    priority: String
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("Konfirmasi Laporan", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
        Spacer(Modifier.height(12.dp))
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SigapSurface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                ConfirmRow(Icons.Outlined.Category, "Kategori") { ConfirmValue(category) }
                ConfirmRow(Icons.Outlined.EditNote, "Judul") { ConfirmValue(title) }
                ConfirmRow(Icons.Outlined.LocationOn, "Lokasi") {
                    ConfirmValue("$building, $floor, $room")
                    if (note.isNotBlank()) ConfirmValue("Keterangan: $note")
                }
                ConfirmRow(Icons.Outlined.Flag, "Prioritas") { PriorityChip(priority) }
            }
        }
    }
}

@Composable
private fun ConfirmRow(icon: ImageVector, label: String, content: @Composable ColumnScope.() -> Unit) {
    Row {
        Icon(icon, null, tint = SigapPrimaryDark, modifier = Modifier.size(26.dp))
        Spacer(Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 14.sp, color = SigapTextMuted)
            Spacer(Modifier.height(2.dp))
            content()
        }
    }
}

@Composable
private fun ConfirmValue(text: String) {
    Text(text, fontSize = 17.sp, color = SigapText)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateReportScreenPreview() {
    SigapTheme { CreateReportScreen({}, {}) }
}