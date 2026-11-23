package com.kampus.sigap.model

import android.net.Uri
import androidx.compose.ui.graphics.Color

enum class ReportStatus(val label: String, val background: Color, val foreground: Color) {
    Waiting("Menunggu Verifikasi", Color(0xFFFFF0C9), Color(0xFFE08A00)),
    Processed("Diproses", Color(0xFFDDF0FD), Color(0xFF0B87D6)),
    InProgress("Dalam Pengerjaan", Color(0xFFECE6FA), Color(0xFF7550C2)),
    Done("Selesai", Color(0xFFDDF3E4), Color(0xFF2E8B57)),
    Rejected("Ditolak", Color(0xFFFDE2E3), Color(0xFFE5484D))
}

data class Report(
    val id: String,
    val title: String,
    val building: String,
    val floor: String,
    val room: String,
    val category: String,
    val priority: String,
    val description: String,
    val status: ReportStatus,
    val photos: List<Int> = emptyList(),
    val photoUris: List<Uri> = emptyList()
) {
    val shortLocation: String get() = "$building, $room"
    val fullLocation: String get() = "$building, $floor, $room"
}