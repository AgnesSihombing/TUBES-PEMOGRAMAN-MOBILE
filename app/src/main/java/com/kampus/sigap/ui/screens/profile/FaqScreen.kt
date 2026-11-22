package com.kampus.sigap.ui.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*
import kotlinx.coroutines.launch

private data class FaqEntry(val id: Int, val question: String, val answer: String)

private val defaultFaqs = listOf(
    FaqEntry(
        1,
        "Bagaimana cara membuat laporan?",
        "Ketuk tombol + di navigasi bawah, lalu isi foto kerusakan, kategori, judul, dan lokasi. Periksa kembali di langkah konfirmasi, kemudian kirim laporanmu."
    ),
    FaqEntry(
        2,
        "Apa arti setiap status laporan?",
        "Menunggu Verifikasi: laporan menunggu diperiksa admin.\nDiproses: laporan sudah diverifikasi.\nDalam Pengerjaan: teknisi sedang memperbaiki.\nSelesai: perbaikan telah selesai.\nDitolak: laporan tidak dapat diproses."
    ),
    FaqEntry(
        3,
        "Berapa lama proses penanganan?",
        "Lama penanganan bergantung pada jenis kerusakan dan ketersediaan teknisi. Pantau perkembangannya di Riwayat Penanganan dan melalui notifikasi."
    ),
    FaqEntry(
        4,
        "Laporan saya ditolak, apa yang harus dilakukan?",
        "Periksa kembali kelengkapan data dan foto laporanmu. Kamu dapat membuat laporan baru dengan data yang lebih jelas."
    ),
    FaqEntry(
        5,
        "Bagaimana cara memberi feedback?",
        "Setelah status laporan Selesai, buka Riwayat Penanganan lalu ketuk tombol Beri Feedback."
    )
)

@Composable
fun FaqScreen(onBack: () -> Unit) {
    val entries = remember { mutableStateListOf<FaqEntry>().apply { addAll(defaultFaqs) } }
    var input by rememberSaveable { mutableStateOf("") }
    var nextId by remember { mutableIntStateOf(100) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun submit() {
        val text = input.trim()
        if (text.isEmpty()) return
        entries.add(FaqEntry(nextId, text, "Pertanyaanmu sudah kami catat dan akan ditinjau oleh admin."))
        nextId += 1
        input = ""
        scope.launch { snackbarHostState.showSnackbar("Pertanyaan berhasil ditambahkan") }
    }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Frequently Asked Question", onBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Tulis Pertanyaan", color = SigapTextMuted) },
                    trailingIcon = {
                        IconButton(onClick = { submit() }) {
                            Icon(Icons.Filled.Add, "Tambah pertanyaan", tint = SigapPrimaryDark, modifier = Modifier.size(28.dp))
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { submit() }),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SigapPrimary,
                        unfocusedBorderColor = SigapBorder,
                        focusedContainerColor = SigapFieldBg,
                        unfocusedContainerColor = SigapFieldBg,
                        cursorColor = SigapPrimary
                    )
                )
            }
            items(entries, key = { it.id }) { entry ->
                FaqItem(entry.question, entry.answer)
            }
        }
    }
}

@Composable
private fun FaqItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 90f else 0f, label = "faqArrow")
    val shape = RoundedCornerShape(14.dp)

    Card(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = SigapSurface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SigapText,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    tint = SigapTextMuted,
                    modifier = Modifier.rotate(rotation)
                )
            }
            AnimatedVisibility(visible = expanded) {
                Text(
                    answer,
                    fontSize = 15.sp,
                    color = SigapTextMuted,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FaqScreenPreview() {
    SigapTheme { FaqScreen({}) }
}