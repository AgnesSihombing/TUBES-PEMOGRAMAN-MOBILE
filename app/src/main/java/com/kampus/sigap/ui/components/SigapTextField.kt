package com.kampus.sigap.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kampus.sigap.ui.theme.*

@Composable
fun SigapTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    errorText: String? = null
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, color = SigapTextMuted) },
        leadingIcon = if (leadingIcon != null) {
            { Icon(leadingIcon, null, tint = SigapPrimary) }
        } else null,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = SigapText
                    )
                }
            }
        } else null,
        supportingText = if (errorText != null) {
            { Text(errorText) }
        } else null,
        isError = errorText != null,
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
        singleLine = singleLine,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SigapPrimary,
            unfocusedBorderColor = SigapBorder,
            focusedContainerColor = SigapFieldBg,
            unfocusedContainerColor = SigapFieldBg,
            cursorColor = SigapPrimary,
            errorContainerColor = SigapFieldBg
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SigapTextFieldPreview() {
    SigapTheme {
        Column(Modifier.padding(16.dp)) {
            SigapTextField("", {}, "Email", leadingIcon = Icons.Outlined.Email)
            SigapTextField("rahasia123", {}, "Kata Sandi", leadingIcon = Icons.Outlined.Lock, isPassword = true)
        }
    }
}