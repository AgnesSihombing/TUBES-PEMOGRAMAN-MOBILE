package com.kampus.sigap.ui.screens.splash


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.kampus.sigap.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val inspection = LocalInspectionMode.current
    val logoAlpha = remember { Animatable(if (inspection) 1f else 0f) }
    val logoScale = remember { Animatable(if (inspection) 1f else 0.5f) }
    val logoOffset = remember { Animatable(if (inspection) 0f else 80f) }
    val textAlpha = remember { Animatable(if (inspection) 1f else 0f) }
    val bgScale = remember { Animatable(if (inspection) 1f else 1.2f) }
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulseValue"
    )

    LaunchedEffect(Unit) {
        if (!inspection) {
            launch { bgScale.animateTo(1f, tween(2800, easing = LinearOutSlowInEasing)) }
            launch { logoAlpha.animateTo(1f, tween(700)) }
            launch { logoOffset.animateTo(0f, tween(800, easing = FastOutSlowInEasing)) }
            launch {
                logoScale.animateTo(
                    1f,
                    spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
                )
            }
            delay(700)
            textAlpha.animateTo(1f, tween(700))
            delay(1300)
            onFinished()
        }
    }

    Box(Modifier.fillMaxSize().background(SigapGradientVertical)) {
        Image(
            painter = painterResource(R.drawable.img_splash_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { scaleX = bgScale.value; scaleY = bgScale.value },
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xE61FA297), Color(0xF20A6F5E))))
        )
        Column(
            Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_sigap_putih),
                contentDescription = "Logo SIGAP",
                modifier = Modifier
                    .width(230.dp)
                    .graphicsLayer {
                        alpha = logoAlpha.value
                        scaleX = logoScale.value * pulse
                        scaleY = logoScale.value * pulse
                        translationY = logoOffset.value
                    }
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Sistem Informasi Gangguan\ndan Aduan Prasarana",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer {
                    alpha = textAlpha.value
                    translationY = (1f - textAlpha.value) * 30f
                }
            )
        }
        Text(
            text = "Versi 1.0.0",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
                .graphicsLayer { alpha = textAlpha.value }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SigapTheme { SplashScreen(onFinished = {}) }
}