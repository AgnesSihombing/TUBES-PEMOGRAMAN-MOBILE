package com.kampus.sigapapp.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.kampus.sigapapp.ui.theme.*

enum class ReportStatus(val label: String, val filter: String, val bg: Color, val fg: Color) {
    MENUNGGU("Menunggu Verifikasi", "Menunggu", WaitBg, WaitFg),
    DIPROSES("Diproses", "Diproses", ProcBg, ProcFg),
    DALAM_PENGERJAAN("Dalam Pengerjaan", "Diproses", WorkBg, WorkFg),
    SELESAI("Selesai", "Selesai", DoneBg, DoneFg),
    DITOLAK("Ditolak", "Ditolak", RejBg, RejFg)
}

data class Report(
    val id: String,            // tanpa '#', contoh: LP-20261108-001
    val title: String,
    val location: String,      // "Gedung A, Ruang A201"
    val fullLocation: String,  // "Gedung A, Lantai 2, Ruang A201"
    val status: ReportStatus,
    val category: String,
    val priority: String,
    val description: String
)

object SampleData {
    val reports = mutableStateListOf(
        Report("LP-20261108-001", "AC tidak dingin", "Gedung A, Ruang A201", "Gedung A, Lantai 2, Ruang A201",
            ReportStatus.MENUNGGU, "AC", "Sedang", "AC tidak mengeluarkan udara dingin sejak kemarin."),
        Report("LP-20261105-004", "Lampu mati", "Gedung B, Ruang B103", "Gedung B, Lantai 1, Ruang B103",
            ReportStatus.DIPROSES, "Listrik", "Sedang", "Lampu di ruangan mati total."),
        Report("LP-20261103-007", "Kursi rusak", "Gedung C, Ruang C301", "Gedung C, Lantai 3, Ruang C301",
            ReportStatus.SELESAI, "Furnitur", "Rendah", "Kaki kursi patah."),
        Report("LP-20261029-012", "Jaringan internet tidak stabil", "Gedung A, Ruang A105", "Gedung A, Lantai 1, Ruang A105",
            ReportStatus.DITOLAK, "Jaringan", "Tinggi", "Wi-Fi sering putus.")
    )
}
