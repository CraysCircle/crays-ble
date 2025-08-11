package me.vivekanand.crayschat.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import me.vivekanand.crayschat.ui.theme.CraysCircleTheme
import me.vivekanand.crayschat.ui.theme.UbuntuFontFamily

/**
 * Permission explanation screen shown before requesting permissions
 * Explains why Crays Circle needs each permission and reassures users about privacy
 */
@Composable
fun PermissionExplanationScreen(
    permissionCategories: List<PermissionCategory>,
    onContinue: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 88.dp) // Leave space for the fixed button
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Crays Circle",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = UbuntuFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Decentralized mesh messaging over Bluetooth",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = UbuntuFontFamily,
                        color = colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Privacy assurance section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                border = BorderStroke(1.dp, colorScheme.onSurface.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "🔒",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Your Privacy is Protected",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onSurface
                            )
                        )
                    }
                    
                    Text(
                        text = "• Crays Circle doesn't track you or collect personal data\n" +
                                "• No servers, no internet required, no data logging\n" +
                                "• Location permission is only used by Android for Bluetooth scanning\n" +
                                "• Your messages stay on your device and peer devices only",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = UbuntuFontFamily,
                            color = colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "To work properly, Crays Circle needs these permissions:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
            )

            // Permission categories
            permissionCategories.forEach { category ->
                PermissionCategoryCard(
                    category = category,
                    colorScheme = colorScheme
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Fixed button at bottom
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = colorScheme.surface,
            border = BorderStroke(1.dp, colorScheme.onSurface.copy(alpha = 0.2f))
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary
                )
            ) {
                Text(
                    text = "Grant Permissions",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = UbuntuFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun PermissionCategoryCard(
    category: PermissionCategory,
    colorScheme: ColorScheme
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        ),
        border = BorderStroke(1.dp, colorScheme.onSurface.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = getPermissionEmoji(category.type),
                    style = MaterialTheme.typography.titleLarge,
                    color = getPermissionIconColor(category.type),
                    modifier = Modifier.size(24.dp)
                )
                
                Text(
                    text = category.type.nameValue,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface
                    )
                )
            }
            
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = UbuntuFontFamily,
                    color = colorScheme.onSurface.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )
            )

            if (category.type == PermissionType.PRECISE_LOCATION) {
                // Extra emphasis for location permission
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "⚠️",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Crays Circle does NOT use GPS or track location",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = UbuntuFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFF9800)
                        )
                    )
                }
            }
        }
    }
}

private fun getPermissionEmoji(permissionType: PermissionType): String {
    return when (permissionType) {
        PermissionType.NEARBY_DEVICES -> "📱"
        PermissionType.PRECISE_LOCATION -> "📍"
        PermissionType.NOTIFICATIONS -> "🔔"
        PermissionType.BATTERY_OPTIMIZATION -> "🔋"
        PermissionType.OTHER -> "🔧"
    }
}

private fun getPermissionIconColor(permissionType: PermissionType): Color {
    return when (permissionType) {
        PermissionType.NEARBY_DEVICES -> Color(0xFF2196F3) // Blue
        PermissionType.PRECISE_LOCATION -> Color(0xFFFF9800) // Orange
        PermissionType.NOTIFICATIONS -> Color(0xFF4CAF50) // Green
        PermissionType.BATTERY_OPTIMIZATION -> Color(0xFFF44336) // Red
        PermissionType.OTHER -> Color(0xFF9C27B0) // Purple
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PermissionExplanationScreenPreview() {
    CraysCircleTheme {
        val sampleCategories = listOf(
            PermissionCategory(
                type = PermissionType.NEARBY_DEVICES,
                description = "Required to discover Crays Circle users via Bluetooth",
                permissions = listOf("BLUETOOTH_CONNECT", "BLUETOOTH_SCAN"),
                isGranted = false,
                systemDescription = "Allow Crays Circle to connect to nearby devices"
            ),
            PermissionCategory(
                type = PermissionType.PRECISE_LOCATION,
                description = "Required by Android to discover nearby Crays Circle users via Bluetooth",
                permissions = listOf("ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION"),
                isGranted = true,
                systemDescription = "Crays Circle needs this to scan for nearby devices"
            ),
            PermissionCategory(
                type = PermissionType.NOTIFICATIONS,
                description = "Receive notifications when you receive private messages",
                permissions = listOf("POST_NOTIFICATIONS"),
                isGranted = false,
                systemDescription = "Allow Crays Circle to send you notifications"
            )
        )
        
        PermissionExplanationScreen(
            permissionCategories = sampleCategories,
            onContinue = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PermissionCategoryCardPreview() {
    CraysCircleTheme {
        PermissionCategoryCard(
            category = PermissionCategory(
                type = PermissionType.PRECISE_LOCATION,
                description = "Required by Android to discover nearby Crays Circle users via Bluetooth",
                permissions = listOf("ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION"),
                isGranted = false,
                systemDescription = "Crays Circle needs this to scan for nearby devices"
            ),
            colorScheme = MaterialTheme.colorScheme
        )
    }
}
