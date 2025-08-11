package me.vivekanand.crayschat.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import me.vivekanand.crayschat.ui.theme.CraysCircleTheme
import me.vivekanand.crayschat.ui.theme.UbuntuFontFamily

/**
 * Screen shown when checking location services status or requesting location services enable
 */
@Composable
fun LocationCheckScreen(
    status: LocationStatus,
    onEnableLocation: () -> Unit,
    onRetry: () -> Unit,
    isLoading: Boolean = false
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            LocationStatus.DISABLED -> {
                LocationDisabledContent(
                    onEnableLocation = onEnableLocation,
                    onRetry = onRetry,
                    colorScheme = colorScheme,
                    isLoading = isLoading
                )
            }
            LocationStatus.NOT_AVAILABLE -> {
                LocationNotAvailableContent(
                    colorScheme = colorScheme
                )
            }
            LocationStatus.ENABLED -> {
                LocationCheckingContent(
                    colorScheme = colorScheme
                )
            }
        }
    }
}

@Composable
private fun LocationDisabledContent(
    onEnableLocation: () -> Unit,
    onRetry: () -> Unit,
    colorScheme: ColorScheme,
    isLoading: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location icon - using LocationOn outlined icon in app's green color
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = "Location Services",
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF00C851) // App's main green color
        )

        Text(
            text = "Location Services Required",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = UbuntuFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            border = BorderStroke(1.dp, colorScheme.onSurface.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Privacy assurance section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Security,
                        contentDescription = "Privacy",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Privacy First",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface
                        )
                    )
                }
                
                Text(
                    text = "Crays Circle does NOT track your location or use GPS.\n\nLocation services are required by Android for Bluetooth scanning to work properly. This is an Android system requirement.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = UbuntuFontFamily,
                        color = colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Crays Circle needs location services for:",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "• Bluetooth device scanning (Android requirement)\n" +
                            "• Discovering nearby users on mesh network\n" +
                            "• Creating connections without internet\n" +
                            "• No GPS tracking or location collection",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = UbuntuFontFamily,
                        color = colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                )
            }
        }

        if (isLoading) {
            LocationLoadingIndicator()
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onEnableLocation,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C851) // App's main green color
                    )
                ) {
                    Text(
                        text = "Open Location Settings",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = UbuntuFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Check Again",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = UbuntuFontFamily
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationNotAvailableContent(
    colorScheme: ColorScheme
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Error icon
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = "Error",
            modifier = Modifier.size(64.dp),
            tint = colorScheme.error
        )

        Text(
            text = "Location Services Unavailable",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = UbuntuFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorScheme.error
            ),
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.errorContainer.copy(alpha = 0.1f)
            ),
            border = BorderStroke(1.dp, colorScheme.error.copy(alpha = 0.3f))
        ) {
            Text(
                text = "Location services are not available on this device. This is unusual as location services are standard on Android devices.\n\nCrays Circle needs location services for Bluetooth scanning to work properly (Android requirement). Without this, the app cannot discover nearby users.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = UbuntuFontFamily,
                    color = colorScheme.onSurface
                ),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LocationCheckingContent(
    colorScheme: ColorScheme
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crays Circle",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = UbuntuFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )

        LocationLoadingIndicator()

        Text(
            text = "Checking location services...",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = UbuntuFontFamily,
                color = colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
private fun LocationLoadingIndicator() {
    // Animated rotation for the loading indicator
    val infiniteTransition = rememberInfiniteTransition(label = "location_loading")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier.size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotationAngle),
            color = Color(0xFF4CAF50), // Location green
            strokeWidth = 3.dp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LocationCheckScreenDisabledPreview() {
    CraysCircleTheme {
        LocationCheckScreen(
            status = LocationStatus.DISABLED,
            onEnableLocation = {},
            onRetry = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LocationCheckScreenLoadingPreview() {
    CraysCircleTheme {
        LocationCheckScreen(
            status = LocationStatus.DISABLED,
            onEnableLocation = {},
            onRetry = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LocationCheckScreenNotAvailablePreview() {
    CraysCircleTheme {
        LocationCheckScreen(
            status = LocationStatus.NOT_AVAILABLE,
            onEnableLocation = {},
            onRetry = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun LocationCheckScreenEnabledPreview() {
    CraysCircleTheme {
        LocationCheckScreen(
            status = LocationStatus.ENABLED,
            onEnableLocation = {},
            onRetry = {},
            isLoading = false
        )
    }
}
