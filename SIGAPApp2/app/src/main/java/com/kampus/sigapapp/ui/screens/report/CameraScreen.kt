package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.theme.*

@Composable
fun CameraScreen(onBack: () -> Unit, onPhotoTaken: () -> Unit) {
    var flashOn by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ScreenHeader(onBack = onBack, title = "Ambil Foto", centerTitle = true)
        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Foto Kerusakan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Arahkan kamera ke bagian yang rusak. Pastikan objek terlihat jelas.",
                fontSize = 15.sp,
                color = TextGray
            )
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.img_camera_preview),
                    contentDescription = "Pratinjau kamera",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x99163A35))
                        .clickable { flashOn = !flashOn }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (flashOn) Icons.Outlined.FlashOn else Icons.Outlined.FlashOff,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = if (flashOn) "Kilat hidup" else "Kilat mati",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Canvas(
                    Modifier
                        .align(Alignment.Center)
                        .size(width = 230.dp, height = 130.dp)
                ) {
                    val length = 28.dp.toPx()
                    val stroke = 3.dp.toPx()
                    val w = size.width
                    val h = size.height
                    fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
                        drawLine(
                            color = Color.White,
                            start = Offset(x1, y1),
                            end = Offset(x2, y2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                    line(0f, 0f, length, 0f)
                    line(0f, 0f, 0f, length)
                    line(w, 0f, w - length, 0f)
                    line(w, 0f, w, length)
                    line(0f, h, length, h)
                    line(0f, h, 0f, h - length)
                    line(w, h, w - length, h)
                    line(w, h, w, h - length)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x99163A35))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = "1×", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(PrimarySoft)
                        .clickable(onClick = onPhotoTaken),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoLibrary,
                        contentDescription = "Galeri",
                        tint = PrimaryDark
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(text = "Galeri", fontSize = 13.sp, color = TextDark)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .border(3.dp, BrandPrimary, CircleShape)
                        .padding(7.dp)
                        .clip(CircleShape)
                        .background(BrandPrimary)
                        .clickable(onClick = onPhotoTaken),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = "Ambil foto",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Ambil foto",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(PrimarySoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cameraswitch,
                        contentDescription = "Putar kamera",
                        tint = PrimaryDark
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(text = "Putar kamera", fontSize = 13.sp, color = TextDark)
            }
        }
        Text(
            text = "Usahakan ponsel tetap stabil saat memotret.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontSize = 13.sp,
            color = TextGray,
            textAlign = TextAlign.Center
        )
    }
}