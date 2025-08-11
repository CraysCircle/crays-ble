package me.vivekanand.crayschat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// Black and white theme only
private val BlackWhiteColorScheme = lightColorScheme(
    primary = Color.Black,              // Black
    onPrimary = Color.White,
    secondary = Color.Black,            // Black
    onSecondary = Color.White,
    background = Color.White,           // White background
    onBackground = Color.Black,         // Black text
    surface = Color.White,              // White surface
    onSurface = Color.Black,            // Black text
    error = Color.Black,                // Black for errors
    onError = Color.White,
    surfaceVariant = Color(0xFFF5F5F5), // Very light gray
    onSurfaceVariant = Color.Black,
    errorContainer = Color(0xFFF5F5F5), // Very light gray
    onErrorContainer = Color.Black
)

@Composable
fun CraysCircleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BlackWhiteColorScheme,
        typography = Typography,
        content = content
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CraysCircleThemePreview() {
    CraysCircleTheme {
        // This will show the theme applied to a simple text
        androidx.compose.material3.Text(
            text = "Crays Circle Theme Preview",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
