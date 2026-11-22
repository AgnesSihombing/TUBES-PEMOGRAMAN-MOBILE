package com.kampus.sigap.model

import androidx.compose.runtime.mutableStateListOf
import com.kampus.sigap.R

object DummyData {
    const val userName = "Martin Simanjuntak"
    const val userId = "241402157"
    const val userEmail = "martin@usu.ac.id"

    val categories = listOf("AC", "Lampu", "Plafon", "Meja", "Kursi", "Jaringan", "Sanitasi", "Lainnya")
    val buildings = listOf("Gedung A", "Gedung B", "Gedung C")
    val floors = listOf("Lantai 1", "Lantai 2", "Lantai 3")
    val userTypes = listOf("Mahasiswa", "Dosen", "Tenaga Kependidikan")

    fun roomsFor(building: String, floor: String): List<String> {
        if (building.isBlank() || floor.isBlank()) return emptyList()
        val letter = building.last()
        val level = floor.last()
        return List(5) { "Ruang $letter${level}0${it + 1}" }
    }

    fun priorityFor(category: String): String = when (category) {
        "Jaringan", "Sanitasi" -> "Tinggi"
        "AC", "Lampu", "Plafon" -> "Sedang"
        else -> "Rendah"
    }

    val reports = mutableStateListOf(
        Report(
            id = "LP-20261108-001",
            title = "AC tidak dingin",
            building = "Gedung A",
            floor = "Lantai 2",
            room = "Ruang A201",
            category = "AC",
            priority = "Sedang",
            description = "AC tidak mengeluarkan udara dingin sejak kemarin.",
            status = ReportStatus.Waiting,
            photos = listOf(R.drawable.img_ac_1, R.drawable.img_ac_2)
        ),
        Report(
            id = "LP-20261105-004",
            title = "Lampu mati",
            building = "Gedung B",
            floor = "Lantai 1",
            room = "Ruang B103",
            category = "Lampu",
            priority = "Sedang",
            description = "Lampu di ruangan mati dan tidak bisa menyala.",
            status = ReportStatus.Processed
        ),
        Report(
            id = "LP-20261103-007",
            title = "Kursi rusak",
            building = "Gedung C",
            floor = "Lantai 3",
            room = "Ruang C301",
            category = "Kursi",
            priority = "Rendah",
            description = "Kaki kursi patah sehingga tidak aman dipakai.",
            status = ReportStatus.Done
        ),
        Report(
            id = "LP-20261029-012",
            title = "Jaringan internet tidak stabil",
            building = "Gedung A",
            floor = "Lantai 1",
            room = "Ruang A105",
            category = "Jaringan",
            priority = "Tinggi",
            description = "Koneksi Wi-Fi sering terputus saat perkuliahan berlangsung.",
            status = ReportStatus.Rejected
        )
    )

    fun findReport(id: String?): Report? = reports.firstOrNull { it.id == id }

    fun addReport(report: Report) {
        reports.add(0, report)
    }
}