package com.example.permissions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "OK")
            }
        },
        title = {
            Text(
                text = "Camera and Microphone permission are needed",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = "This app needs access to your camera and microphone"
            )
        }
    )
}