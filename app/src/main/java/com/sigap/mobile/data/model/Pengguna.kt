package com.sigap.mobile.data.model

// idLabel = nama nomor identitas yang tampil di UI (NIM / NIP / ID)
enum class Peran(val label: String, val idLabel: String) {
    Mahasiswa("Mahasiswa", "NIM"),
    Dosen("Dosen", "NIP"),
    Staff("Staff", "NIP"),
    Admin("Admin", "ID")
}

data class Pengguna(
    val id: String,
    val nama: String,
    val email: String,
    val peran: Peran,
    val aktif: Boolean = true,
    val bergabung: String = "",
    val terakhirMasuk: String = ""
)