package com.sigap.mobile.data.model

// Status laporan. Nama dan labelnya harus disepakati dengan tim (FE mahasiswa & BE).
enum class LaporanStatus(val label: String) {
    MenungguVerifikasi("Menunggu Verifikasi"),
    Diproses("Diproses"),
    Selesai("Selesai"),
    Ditolak("Ditolak")
}

// Satu data laporan. Nanti diisi dari Firebase.
data class Laporan(
    val id: String,
    val judul: String,
    val lokasi: String,
    val status: LaporanStatus
)