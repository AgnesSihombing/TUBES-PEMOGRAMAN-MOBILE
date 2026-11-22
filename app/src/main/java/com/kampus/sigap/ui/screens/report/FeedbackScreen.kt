package com.kampus.sigap.ui.screens.report

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTextField
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*

@Composable
fun FeedbackScreen(onBack: () -> Unit, onSubmitted: () -> Unit) {
    var rating by rememberSaveable { mutableIntStateOf(0) }
    var comment by rememberSaveable { mutableStateOf("") }
    var showThanks by remember { mutableStateOf(false) }

    if (showThanks) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Terima kasih!", fontWeight = FontWeight.Bold) },
            text = { Text("Feedback-mu sudah kami terima dan sangat membantu kami meningkatkan layanan.") },
            confirmButton = {
                TextButton(onClick = onSubmitted) {
                    Text("Tutup", color = SigapPrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Beri Feedback", onBack) },
        bottomBar = {
            Box(Modifier.fillMaxWidth().navigationBarsPadding().imePadding().padding(20.dp)) {
                SigapPrimaryButton(
                    "Kirim Feedback",
                    { showThanks = true },
                    Modifier.fillMaxWidth(),
                    enabled = rating > 0
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.img_feedback_mascot),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(Modifier.height(32.dp))
            Text(
                "Bagaimana penanganan laporan ini?",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SigapText,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { value ->
                    val filled = value <= rating
                    val scale by animateFloatAsState(
                        targetValue = if (value == rating) 1.3f else 1f,
                        animationSpec = spring(Spring.DampingRatioMediumBouncy),
                        label = "star"
                    )
                    Icon(
                        imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Bintang $value",
                        tint = if (filled) Color(0xFFF5A300) else Color(0xFF9AA5B1),
                        modifier = Modifier
                            .size(44.dp)
                            .graphicsLayer { scaleX = scale; scaleY = scale }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { rating = value }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            SigapTextField(
                value = comment,
                onValueChange = { comment = it.take(200) },
                placeholder = "Tulis komentarmu...\n(Opsional)",
                modifier = Modifier.height(130.dp),
                singleLine = false
            )
            Text(
                "${comment.length}/200",
                fontSize = 13.sp,
                color = SigapTextMuted,
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FeedbackScreenPreview() {
    SigapTheme { FeedbackScreen({}, {}) }
}