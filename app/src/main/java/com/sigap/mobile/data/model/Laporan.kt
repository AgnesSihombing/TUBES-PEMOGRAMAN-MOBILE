package com.sigap.mobile.data.model

enum class LaporanStatus(val label: String) {
    MenungguVerifikasi("Menunggu Verifikasi"),
    Diproses("Diproses"),
    Selesai("Selesai"),
    Ditolak("Ditolak")
}

// Prioritas ditentukan admin saat verifikasi
enum class Prioritas(val label: String) {
    Rendah("Rendah"),
    Sedang("Sedang"),
    Tinggi("Tinggi")
}

// Field baru diberi nilai default, jadi LaporanListScreen tetap jalan tanpa diubah
data class Laporan(
    val id: String,
    val judul: String,
    val lokasi: String,
    val status: LaporanStatus,
    val kategori: String = "",
    val deskripsi: String = ""
)