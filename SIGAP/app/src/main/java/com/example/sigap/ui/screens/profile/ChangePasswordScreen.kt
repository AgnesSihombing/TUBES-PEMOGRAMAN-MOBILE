package com.example.sigap.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sigap.ui.theme.SIGAPTheme
import com.example.sigap.ui.theme.SigapPrimary

private val TextDark = Color(0xFF1F2937)
private val TextGray = Color(0xFF6B7280)
private val BorderGray = Color(0xFFE5E7EB)
private val ErrorRed = Color(0xFFDC2626)

private fun cpEye(name: String, path: String): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).addPath(
        pathData = PathParser().parsePathString(path).toNodes(),
        fill = SolidColor(Color.Black)
    ).build()

private val CpEyeVisible: ImageVector = cpEye(
    "CpEyeVisible",
    "M12,4.5C7,4.5 2.73,7.61 1,12c1.73,4.39 6,7.5 11,7.5s9.27,-3.11 11,-7.5c-1.73,-4.39 -6,-7.5 -11,-7.5zM12,17c-2.76,0 -5,-2.24 -5,-5s2.24,-5 5,-5 5,2.24 5,5 -2.24,5 -5,5zM12,9c-1.66,0 -3,1.34 -3,3s1.34,3 3,3 3,-1.34 3,-3 -1.34,-3 -3,-3z"
)

private val CpEyeHidden: ImageVector = cpEye(
    "CpEyeHidden",
    "M12,7c2.76,0 5,2.24 5,5 0,0.65 -0.13,1.26 -0.36,1.83l2.92,2.92c1.51,-1.26 2.7,-2.89 3.43,-4.75 -1.73,-4.39 -6,-7.5 -11,-7.5 -1.4,0 -2.74,0.25 -3.98,0.7l2.16,2.16C10.74,7.13 11.35,7 12,7zM2,4.27l2.28,2.28 0.46,0.46C3.08,8.3 1.78,10.02 1,12c1.73,4.39 6,7.5 11,7.5 1.55,0 3.03,-0.3 4.38,-0.84l0.42,0.42L19.73,22 21,20.73 3.27,3 2,4.27zM7.53,9.8l1.55,1.55c-0.05,0.21 -0.08,0.43 -0.08,0.65 0,1.66 1.34,3 3,3 0.22,0 0.44,-0.03 0.65,-0.08l1.55,1.55c-0.67,0.33 -1.41,0.53 -2.2,0.53 -2.76,0 -5,-2.24 -5,-5 0,-0.79 0.2,-1.53 0.53,-2.2zM11.84,9.02l3.15,3.15 0.02,-0.16c0,-1.66 -1.34,-3 -3,-3l-0.17,0.01z"
)

@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    val isLongEnough = newPassword.length >= 8
    val hasUpper = newPassword.any { it.isUpperCase() }
    val hasLower = newPassword.any { it.isLowerCase() }
    val hasDigit = newPassword.any { it.isDigit() }
    val isNewValid = isLongEnough && hasUpper && hasLower && hasDigit
    val isMatch = newPassword == confirmPassword

    var submitted by rememberSaveable { mutableStateOf(false) }

    val currentError = if (submitted && currentPassword.isEmpty()) {
        "Kata sandi saat ini wajib diisi"
    } else null
    val newPasswordError = when {
        newPassword.isEmpty() && submitted -> "Kata sandi baru wajib diisi"
        newPassword.isNotEmpty() && !isNewValid ->
            "Minimal 8 karakter, kombinasi huruf besar, huruf kecil, dan angka"
        else -> null
    }
    val confirmError = when {
        confirmPassword.isEmpty() && submitted -> "Konfirmasi kata sandi wajib diisi"
        confirmPassword.isNotEmpty() && !isMatch -> "Konfirmasi kata sandi tidak cocok"
        else -> null
    }

    val canSubmit = currentPassword.isNotEmpty() &&
            isNewValid &&
            confirmPassword.isNotEmpty() &&
            isMatch

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextDark,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Ubah Kata Sandi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Jaga keamanan akun Anda dengan mengganti kata sandi secara berkala.",
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 19.sp
            )

            PasswordField(
                label = "Kata Sandi Saat Ini",
                placeholder = "Masukkan kata sandi saat ini",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                errorMessage = currentError
            )

            PasswordField(
                label = "Kata Sandi Baru",
                placeholder = "Masukkan kata sandi baru",
                value = newPassword,
                onValueChange = { newPassword = it },
                errorMessage = newPasswordError
            )

            PasswordField(
                label = "Konfirmasi Kata Sandi Baru",
                placeholder = "Ulangi kata sandi baru",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                errorMessage = confirmError
            )

            // Info box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SigapPrimary.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, SigapPrimary.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = SigapPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Gunakan minimal 8 karakter dengan kombinasi huruf besar, huruf kecil, dan angka.",
                        fontSize = 12.sp,
                        color = TextDark,
                        lineHeight = 17.sp
                    )
                }
            }

            Button(
                onClick = {
                    submitted = true
                    if (canSubmit) {
                        // TODO: panggil ViewModel / API ubah kata sandi
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SigapPrimary)
            ) {
                Text(
                    text = "Simpan Perubahan",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun PasswordField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null
) {
    var visible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage != null,
            placeholder = {
                Text(text = placeholder, fontSize = 13.sp, color = Color(0xFF9CA3AF))
            },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = TextDark),
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                Icon(
                    imageVector = if (visible) CpEyeVisible else CpEyeHidden,
                    contentDescription = if (visible) "Sembunyikan kata sandi" else "Tampilkan kata sandi",
                    tint = TextGray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { visible = !visible }
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SigapPrimary,
                unfocusedBorderColor = BorderGray,
                errorBorderColor = ErrorRed,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = SigapPrimary
            )
        )
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = errorMessage, fontSize = 11.sp, color = ErrorRed)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ChangePasswordScreenPreview() {
    SIGAPTheme {
        ChangePasswordScreen(navController = rememberNavController())
    }
}