package com.kampus.sigap.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigap.R
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.abs

private data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

private val pages = listOf(
    OnboardingPage(
        R.drawable.img_onboarding_1,
        "Laporkan Kerusakan Fasilitas di Lingkungan Kampus",
        "Bantu menjaga kenyamanan belajar dan bekerja dengan melaporkan kerusakan fasilitas."
    ),
    OnboardingPage(
        R.drawable.img_onboarding_2,
        "Pantau Proses Penanganan",
        "Lihat perkembangan laporanmu secara real-time dan transparan."
    ),
    OnboardingPage(
        R.drawable.img_onboarding_3,
        "Dapatkan Notifikasi",
        "Terima pemberitahuan setiap kali ada perubahan status laporan."
    )
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    Scaffold(containerColor = SigapBackground) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(
                Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = !isLast, enter = fadeIn(), exit = fadeOut()) {
                    TextButton(onClick = onFinished) {
                        Text("Lewati", color = SigapPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
                OnboardingPageContent(pages[index], index, pagerState)
            }
            DotsIndicator(
                count = pages.size,
                current = pagerState.currentPage,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(24.dp))
            SigapPrimaryButton(
                text = if (isLast) "Mulai" else "Lanjut",
                onClick = {
                    if (isLast) onFinished()
                    else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage, index: Int, pagerState: PagerState) {
    val inspection = LocalInspectionMode.current
    val intro = remember { Animatable(if (inspection || index != 0) 1f else 0f) }
    val floating by rememberInfiniteTransition(label = "float").animateFloat(
        initialValue = -14f,
        targetValue = 14f,
        animationSpec = infiniteRepeatable(tween(1800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "floatValue"
    )
    val pageOffset: () -> Float = {
        (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
    }

    LaunchedEffect(Unit) {
        if (!inspection && index == 0) intro.animateTo(1f, tween(900, easing = FastOutSlowInEasing))
    }

    Column(
        Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(280.dp)
                .graphicsLayer {
                    val offset = pageOffset()
                    val visibility = (1f - abs(offset)).coerceIn(0f, 1f)
                    alpha = visibility * intro.value
                    val s = (1f - 0.25f * abs(offset)) * (0.8f + 0.2f * intro.value)
                    scaleX = s
                    scaleY = s
                    translationX = offset * size.width * 0.35f
                    translationY = floating
                }
        )
        Spacer(Modifier.height(32.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                val offset = pageOffset()
                alpha = (1f - abs(offset) * 1.6f).coerceIn(0f, 1f) * intro.value
                translationX = offset * size.width * 0.15f
                translationY = (1f - intro.value) * 70f
            }
        ) {
            Text(
                text = page.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SigapText,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = page.description,
                fontSize = 16.sp,
                color = SigapTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
        Spacer(Modifier.weight(1.2f))
    }
}

@Composable
private fun DotsIndicator(count: Int, current: Int, modifier: Modifier = Modifier) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(count) { index ->
            val width by animateDpAsState(if (index == current) 28.dp else 8.dp, label = "dotWidth")
            val color by animateColorAsState(
                if (index == current) SigapPrimary else SigapPrimary.copy(alpha = 0.3f),
                label = "dotColor"
            )
            Box(Modifier.height(8.dp).width(width).clip(CircleShape).background(color))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    SigapTheme { OnboardingScreen(onFinished = {}) }
}