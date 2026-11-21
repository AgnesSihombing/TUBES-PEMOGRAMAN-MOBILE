package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.ui.components.GradientButton
import com.kampus.sigapapp.ui.theme.*
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String,
    val large: Boolean
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages = listOf(
        OnboardingPage(
            R.drawable.img_onboarding_1,
            "Laporkan\nKerusakan Fasilitas\ndi Lingkungan Kampus",
            "Bantu menjaga kenyamanan\nbelajar dan bekerja dengan\nmelaporkan kerusakan fasilitas.",
            true
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_2,
            "Pantau\nProses Penanganan",
            "Lihat perkembangan\nlaporanmu secara real-time\ndan transparan.",
            false
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_3,
            "Dapatkan\nNotifikasi",
            "Terima pemberitahuan\nsetiap kali ada perubahan\nstatus laporan.",
            false
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    Column(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(page.image),
                    contentDescription = null,
                    modifier = if (page.large) {
                        Modifier.fillMaxWidth().height(300.dp)
                    } else {
                        Modifier.size(170.dp)
                    },
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(40.dp))
                Text(
                    text = page.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                Box(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) BrandPrimary
                            else BrandSecondary.copy(alpha = 0.45f)
                        )
                )
            }
        }
        GradientButton(
            text = if (isLast) "Mulai" else "Lanjut",
            onClick = {
                if (isLast) onFinished()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        )
    }
}