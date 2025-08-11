package me.vivekanand.crayschat.ui.theme

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TypographyPreview() {
    CraysCircleTheme {
        Column {
            androidx.compose.material3.Text(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge
            )
            androidx.compose.material3.Text(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium
            )
            androidx.compose.material3.Text(
                text = "Headline Small",
                style = MaterialTheme.typography.headlineSmall
            )
            androidx.compose.material3.Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.material3.Text(
                text = "Title Medium",
                style = MaterialTheme.typography.titleMedium
            )
            androidx.compose.material3.Text(
                text = "Title Small",
                style = MaterialTheme.typography.titleSmall
            )
            androidx.compose.material3.Text(
                text = "Body Large",
                style = MaterialTheme.typography.bodyLarge
            )
            androidx.compose.material3.Text(
                text = "Body Medium",
                style = MaterialTheme.typography.bodyMedium
            )
            androidx.compose.material3.Text(
                text = "Body Small",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 