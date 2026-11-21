package com.kampus.sigapapp.ui.screens.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.data.DummyData
import com.kampus.sigapapp.data.FaqItem
import com.kampus.sigapapp.ui.components.ScreenHeader
import com.kampus.sigapapp.ui.components.SigapTextField
import com.kampus.sigapapp.ui.theme.*

@Composable
fun FaqScreen(onBack: () -> Unit) {
    var question by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
    ) {
        ScreenHeader(onBack = onBack, title = "Frequently Asked Question")
        Box(Modifier.padding(horizontal = 20.dp)) {
            SigapTextField(
                value = question,
                onValueChange = { question = it },
                placeholder = "Tulis Pertanyaan",
                endIcon = Icons.Outlined.Add,
                onEndIconClick = { question = "" }
            )
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(DummyData.faqs) { FaqCard(it) }
        }
    }
}

@Composable
private fun FaqCard(item: FaqItem) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(14.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppSurface)
            .border(1.dp, LineColor, shape)
            .clickable { expanded = !expanded }
            .animateContentSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.question,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextGray,
                modifier = Modifier.rotate(if (expanded) 90f else 0f)
            )
        }
        if (expanded) {
            Spacer(Modifier.height(8.dp))
            Text(text = item.answer, fontSize = 14.sp, color = TextGray, lineHeight = 21.sp)
        }
    }
}