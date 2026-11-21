package com.kampus.sigapapp.ui.screens.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*

@Composable
fun FeedbackScreen(onBack: () -> Unit, onSubmit: () -> Unit) {
    var rating by rememberSaveable { mutableIntStateOf(4) }
    var comment by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        ScreenHeader(onBack = onBack, title = "Beri Feedback")
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Image(
                painter = painterResource(R.drawable.img_feedback_mascot),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = "Bagaimana penanganan\nlaporan ini?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { index ->
                    val filled = index <= rating
                    Icon(
                        imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Bintang $index",
                        tint = if (filled) BrandAccent else StarEmpty,
                        modifier = Modifier
                            .size(44.dp)
                            .clickable { rating = index }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            SigapTextField(
                value = comment,
                onValueChange = { if (it.length <= 200) comment = it },
                placeholder = "Tulis komentarmu...\n(Opsional)",
                singleLine = false,
                minHeight = 120.dp
            )
            Text(
                text = "${comment.length}/200",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp),
                fontSize = 13.sp,
                color = TextGray
            )
        }
        GradientButton(
            text = "Kirim Feedback",
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}