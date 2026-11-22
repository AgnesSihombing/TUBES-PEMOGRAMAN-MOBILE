package com.kampus.sigap.ui.screens.report

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview as CameraPreview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.FlipCameraAndroid
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.kampus.sigap.R
import com.kampus.sigap.ui.components.SigapPrimaryButton
import com.kampus.sigap.ui.components.SigapTopBar
import com.kampus.sigap.ui.theme.*
import java.io.File

@Composable
fun CameraScreen(onBack: () -> Unit, onPhotoTaken: (Uri) -> Unit) {
    BackHandler(onBack = onBack)

    val context = LocalContext.current
    val inspection = LocalInspectionMode.current
    val lifecycleOwner = context as? LifecycleOwner

    var hasPermission by remember {
        mutableStateOf(
            inspection || ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    var flashOn by remember { mutableStateOf(false) }
    var zoomed by remember { mutableStateOf(false) }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }
    var camera by remember { mutableStateOf<Camera?>(null) }

    val previewView = remember { if (inspection) null else PreviewView(context) }
    val imageCapture = remember {
        if (inspection) null
        else ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        hasPermission = it
    }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) onPhotoTaken(uri)
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    DisposableEffect(hasPermission, lensFacing) {
        if (hasPermission && previewView != null && imageCapture != null && lifecycleOwner != null) {
            val future = ProcessCameraProvider.getInstance(context)
            future.addListener({
                runCatching {
                    val provider = future.get()
                    val preview = CameraPreview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val selector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
                    provider.unbindAll()
                    camera = provider.bindToLifecycle(lifecycleOwner, selector, preview, imageCapture)
                }
            }, ContextCompat.getMainExecutor(context))
        }
        onDispose {
            runCatching { ProcessCameraProvider.getInstance(context).get().unbindAll() }
        }
    }

    LaunchedEffect(flashOn) {
        imageCapture?.flashMode = if (flashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
    }

    LaunchedEffect(zoomed, camera) {
        camera?.cameraControl?.setZoomRatio(if (zoomed) 2f else 1f)
    }

    fun capture() {
        val capture = imageCapture ?: return
        val file = File(context.cacheDir, "sigap_${System.currentTimeMillis()}.jpg")
        capture.takePicture(
            ImageCapture.OutputFileOptions.Builder(file).build(),
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onPhotoTaken(Uri.fromFile(file))
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    Scaffold(
        containerColor = SigapBackground,
        topBar = { SigapTopBar("Ambil Foto", onBack, centerTitle = true) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Text("Foto Kerusakan", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = SigapText)
            Spacer(Modifier.height(4.dp))
            Text(
                "Arahkan kamera ke bagian yang rusak. Pastikan objek terlihat jelas.",
                fontSize = 15.sp,
                color = SigapTextMuted,
                lineHeight = 22.sp
            )
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF1F2937))
            ) {
                if (inspection) {
                    Image(
                        painter = painterResource(R.drawable.img_ac_1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (hasPermission && previewView != null) {
                    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
                } else {
                    Column(
                        Modifier.align(Alignment.Center).padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Izin kamera diperlukan untuk mengambil foto.",
                            color = Color.White,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        SigapPrimaryButton(
                            "Berikan Izin",
                            { permissionLauncher.launch(Manifest.permission.CAMERA) },
                            Modifier.width(180.dp)
                        )
                    }
                }

                Canvas(Modifier.align(Alignment.Center).size(width = 240.dp, height = 190.dp)) {
                    val length = 28.dp.toPx()
                    val stroke = 3.dp.toPx()
                    val w = size.width
                    val h = size.height
                    fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
                        drawLine(Color.White, Offset(x1, y1), Offset(x2, y2), stroke, StrokeCap.Round)
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

                Row(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x992A3B3A))
                        .clickable { flashOn = !flashOn }
                        .padding(horizontal = 14.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (flashOn) Icons.Outlined.FlashOn else Icons.Outlined.FlashOff,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (flashOn) "Kilat nyala" else "Kilat mati",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    if (zoomed) "2×" else "1×",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0x992A3B3A))
                        .clickable { zoomed = !zoomed }
                        .padding(horizontal = 18.dp, vertical = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CameraControl(Icons.Outlined.PhotoLibrary, "Galeri", RoundedCornerShape(14.dp)) {
                        galleryLauncher.launch("image/*")
                    }
                }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    ShutterButton(enabled = hasPermission, onClick = { capture() })
                    Spacer(Modifier.height(6.dp))
                    Text("Ambil foto", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SigapPrimary)
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CameraControl(Icons.Outlined.FlipCameraAndroid, "Putar kamera", CircleShape) {
                        zoomed = false
                        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                            CameraSelector.LENS_FACING_FRONT
                        } else {
                            CameraSelector.LENS_FACING_BACK
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "Usahakan ponsel tetap stabil saat memotret.",
                fontSize = 13.sp,
                color = SigapTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun CameraControl(icon: ImageVector, label: String, shape: Shape, onClick: () -> Unit) {
    Column(
        Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.size(52.dp).clip(shape).background(SigapTint),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, label, tint = SigapPrimary, modifier = Modifier.size(26.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(label, fontSize = 13.sp, color = SigapText)
    }
}

@Composable
private fun ShutterButton(enabled: Boolean, onClick: () -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.9f else 1f, label = "shutter")
    Box(
        Modifier
            .size(92.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .border(3.dp, SigapPrimary, CircleShape)
            .padding(7.dp)
            .clip(CircleShape)
            .background(SigapPrimary)
            .clickable(interactionSource = interaction, indication = null, enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Filled.CameraAlt, "Ambil foto", tint = Color.White, modifier = Modifier.size(38.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CameraScreenPreview() {
    SigapTheme { CameraScreen({}, {}) }
}