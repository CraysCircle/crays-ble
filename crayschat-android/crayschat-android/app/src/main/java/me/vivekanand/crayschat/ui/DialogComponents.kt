package me.vivekanand.crayschat.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import me.vivekanand.crayschat.R
import me.vivekanand.crayschat.ui.theme.CraysCircleTheme
import me.vivekanand.crayschat.ui.theme.UbuntuFontFamily

/**
 * Dialog components for ChatScreen
 * Extracted from ChatScreen.kt for better organization
 */

@Composable
fun PasswordPromptDialog(
    show: Boolean,
    channelName: String?,
    passwordInput: String,
    onPasswordChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show && channelName != null) {
        val colorScheme = MaterialTheme.colorScheme
        
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Enter Channel Password",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.onSurface
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter password for channel: $channelName",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = UbuntuFontFamily
                        ),
                        color = colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = onPasswordChange,
                        label = { Text("Password", style = MaterialTheme.typography.bodyMedium) },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = UbuntuFontFamily
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorScheme.primary,
                            unfocusedBorderColor = colorScheme.outline
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = "Join",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurface
                    )
                }
            },
            containerColor = colorScheme.surface,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = Modifier.border(
                width = 1.dp,
                color = colorScheme.onSurface.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
        )
    }
}

@Composable
fun AppInfoDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        val colorScheme = MaterialTheme.colorScheme
        
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "About Crays Circle",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = "Decentralized mesh messaging over Bluetooth LE\n\n" +
                            "• No servers or internet required\n" +
                            "• End-to-end encrypted private messages\n" +
                            "• Password-protected channels\n" +
                            "• Store-and-forward for offline peers\n\n" +
                            "Triple-click title to emergency clear all data",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = UbuntuFontFamily
                    ),
                    color = colorScheme.onSurface
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.primary
                    )
                }
            },
            containerColor = colorScheme.surface,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = Modifier.border(
                width = 1.dp,
                color = colorScheme.onSurface.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PasswordPromptDialogPreview() {
    CraysCircleTheme {
        PasswordPromptDialog(
            show = true,
            channelName = "general",
            passwordInput = "mypassword",
            onPasswordChange = {},
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AppInfoDialogPreview() {
    CraysCircleTheme {
        AppInfoDialog(
            show = true,
            onDismiss = {}
        )
    }
}
