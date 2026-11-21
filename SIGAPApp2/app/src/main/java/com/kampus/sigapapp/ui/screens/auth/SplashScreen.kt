package com.kampus.sigapapp.ui.screens.auth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kampus.sigapapp.R
import com.kampus.sigapapp.ui.components.SigapLogo
import com.kampus.sigapapp.ui.theme.BrandPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val logoScale = remember { Animatable(0.4f) }
    val logoAlpha = remember { Animatable(0f) }
    val logoRotation = remember { Animatable(-15f) }
    val textAlpha = remember { Animatable(0f) }
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseValue"
    )

    LaunchedEffect(Unit) {
        launch { logoAlpha.animateTo(1f, tween(700)) }
        launch {
            logoRotation.animateTo(
                0f,
                spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
            )
        }
        launch {
            logoScale.animateTo(
                1f,
                spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
            )
        }
        delay(900)
        textAlpha.animateTo(1f, tween(600))
        delay(1400)
        onFinished()
    }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_splash_building),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(BrandPrimary.copy(alpha = 0.85f))
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SigapLogo(
                white = true,
                modifier = Modifier
                    .width(260.dp)
                    .graphicsLayer {
                        scaleX = logoScale.value * pulse
                        scaleY = logoScale.value * pulse
                        alpha = logoAlpha.value
                        rotationZ = logoRotation.value
                    }
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Sistem Informasi Gangguan\ndan Aduan Prasarana",
                modifier = Modifier.alpha(textAlpha.value),
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
        }
        Text(
            text = "Versi 1.0.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .alpha(textAlpha.value),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}