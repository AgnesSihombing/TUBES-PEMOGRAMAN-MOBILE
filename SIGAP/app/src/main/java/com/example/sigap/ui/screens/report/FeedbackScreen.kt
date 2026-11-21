package com.example.sigap.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.R
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

// ---------- Warna lokal (samakan dengan Figma) ----------
private val TextPrimary = Color(0xFF1F2937)
private val TextHint = Color(0xFF9CA3AF)
private val BorderColor = Color(0xFFE5E7EB)
private val FieldBg = Color(0xFFF9FAFB)
private val StarOn = Color(0xFFF59E0B)
private val StarOff = Color(0xFFD1D5DB)
private val ButtonStart = Color(0xFF26A69A)

private const val MAX_COMMENT = 200
private const val MAX_STARS = 5

// ---------- Screen ----------
@Composable
fun FeedbackScreen(
    navController: NavHostController,
    initialRating: Int = 0 // Ditambahkan agar mendukung parameter preview
) {
    var rating by rememberSaveable { mutableIntStateOf(initialRating) }
    var comment by rememberSaveable { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        // Status bar (jam, sinyal, baterai) memakai milik HP asli
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            SubmitButton(
                enabled = rating > 0,
                onClick = {
                    // TODO BE: kirim rating & komentar
                    navController.popBackStack()
                },
                modifier = Modifier
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Beri Feedback",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.img_feedback_mascot),
                contentDescription = "Maskot SIGAP",
                modifier = Modifier.size(110.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Bagaimana penanganan\nlaporan ini?",
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            StarRating(rating = rating, onRatingChange = { rating = it })

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = { if (it.length <= MAX_COMMENT) comment = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = {
                    Text(
                        text = "Tulis komentarmu...\n(Opsional)",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = TextHint
                    )
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 13.sp,
                    color = TextPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = FieldBg,
                    unfocusedContainerColor = FieldBg,
                    focusedBorderColor = SigapPrimary,
                    unfocusedBorderColor = BorderColor,
                    cursorColor = SigapPrimary
                )
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${comment.length}/$MAX_COMMENT",
                fontSize = 11.sp,
                color = TextHint,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ---------- Komponen ----------
@Composable
private fun StarRating(rating: Int, onRatingChange: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        for (star in 1..MAX_STARS) {
            val filled = star <= rating
            Icon(
                imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Bintang $star",
                tint = if (filled) StarOn else StarOff,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChange(star) }
            )
        }
    }
}

@Composable
private fun SubmitButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val brush = if (enabled) {
        Brush.horizontalGradient(listOf(ButtonStart, SigapPrimary))
    } else {
        Brush.horizontalGradient(listOf(BorderColor, BorderColor))
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = TextHint
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(brush, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Kirim Feedback",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeedbackScreenPreview() {
    SIGAPTheme {
        FeedbackScreen(rememberNavController(), initialRating = 4)
    }
}