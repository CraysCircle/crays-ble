package me.vivekanand.crayschat.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vivekanand.crayschat.R
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import me.vivekanand.crayschat.ui.theme.CraysCircleTheme
import me.vivekanand.crayschat.ui.theme.UbuntuFontFamily

/**
 * Input components for ChatScreen
 * Extracted from ChatScreen.kt for better organization
 */

/**
 * VisualTransformation that styles slash commands with background and color
 * while preserving cursor positioning and click handling
 */
class SlashCommandVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val slashCommandRegex = Regex("(/\\w+)(?=\\s|$)")
        val annotatedString = buildAnnotatedString {
            var lastIndex = 0

            slashCommandRegex.findAll(text.text).forEach { match ->
                // Add text before the match
                if (match.range.first > lastIndex) {
                    append(text.text.substring(lastIndex, match.range.first))
                }

                // Add the styled slash command
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF00FF7F), // Bright green
                        fontFamily = UbuntuFontFamily,
                        fontWeight = FontWeight.Medium,
                        background = Color(0xFF2D2D2D) // Dark gray background
                    )
                ) {
                    append(match.value)
                }

                lastIndex = match.range.last + 1
            }

            // Add remaining text
            if (lastIndex < text.text.length) {
                append(text.text.substring(lastIndex))
            }
        }

        return TransformedText(
            text = annotatedString,
            offsetMapping = OffsetMapping.Identity
        )
    }
}

/**
 * VisualTransformation that styles mentions with background and color
 * while preserving cursor positioning and click handling
 */
class MentionVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val mentionRegex = Regex("@([a-zA-Z0-9_]+)")
        val annotatedString = buildAnnotatedString {
            var lastIndex = 0
            
            mentionRegex.findAll(text.text).forEach { match ->
                // Add text before the match
                if (match.range.first > lastIndex) {
                    append(text.text.substring(lastIndex, match.range.first))
                }
                
                // Add the styled mention
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFFF9500), // Orange
                        fontFamily = UbuntuFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(match.value)
                }
                
                lastIndex = match.range.last + 1
            }
            
            // Add remaining text
            if (lastIndex < text.text.length) {
                append(text.text.substring(lastIndex))
            }
        }
        
        return TransformedText(
            text = annotatedString,
            offsetMapping = OffsetMapping.Identity
        )
    }
}

/**
 * VisualTransformation that combines multiple visual transformations
 */
class CombinedVisualTransformation(private val transformations: List<VisualTransformation>) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var resultText = text
        
        // Apply each transformation in order
        transformations.forEach { transformation ->
            resultText = transformation.filter(resultText).text
        }
        
        return TransformedText(
            text = resultText,
            offsetMapping = OffsetMapping.Identity
        )
    }
}





@Composable
fun MessageInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSend: () -> Unit,
    selectedPrivatePeer: String?,
    currentChannel: String?,
    nickname: String,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val isFocused = remember { mutableStateOf(false) }
    val hasText = value.text.isNotBlank() // Check if there's text for send button state
    
    Row(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp), // Reduced padding
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Text input with placeholder
        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = colorScheme.onSurface.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = colorScheme.primary,
                    fontFamily = UbuntuFontFamily
                ),
                cursorBrush = SolidColor(colorScheme.primary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { 
                    if (hasText) onSend() // Only send if there's text
                }),
                visualTransformation = CombinedVisualTransformation(
                    listOf(SlashCommandVisualTransformation(), MentionVisualTransformation())
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused.value = focusState.isFocused
                    }
            )
            
            // Show placeholder when there's no text
            if (value.text.isEmpty()) {
                Text(
                    text = "type a message...",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = UbuntuFontFamily
                    ),
                    color = colorScheme.onSurface.copy(alpha = 0.5f), // Muted grey
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp)) // Reduced spacing
        
        // Command quick access button
        if (value.text.isEmpty()) {
            FilledTonalIconButton(
                onClick = {
                    onValueChange(TextFieldValue(text = "/", selection = TextRange("/".length)))
                },
                modifier = Modifier.size(32.dp)
            ) {
                Text(
                    text = "/",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Send button with enabled/disabled state
            IconButton(
                onClick = { if (hasText) onSend() }, // Only execute if there's text
                enabled = hasText, // Enable only when there's text
                modifier = Modifier.size(32.dp)
            ) {
                // Update send button to match input field colors
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = if (!hasText) {
                                // Disabled state - muted grey
                                colorScheme.onSurface.copy(alpha = 0.3f)
                            } else if (selectedPrivatePeer != null || currentChannel != null) {
                                // Orange for both private messages and channels when enabled
                                Color(0xFFFF9500).copy(alpha = 0.75f)
                            } else if (colorScheme.background == Color.Black) {
                                Color(0xFF00FF00).copy(alpha = 0.75f) // Bright green for dark theme
                            } else {
                                Color(0xFF008000).copy(alpha = 0.75f) // Dark green for light theme
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowUpward,
                        contentDescription = stringResource(id = R.string.send_message),
                        modifier = Modifier.size(20.dp),
                        tint = if (!hasText) {
                            // Disabled state - muted grey icon
                            colorScheme.onSurface.copy(alpha = 0.5f)
                        } else if (selectedPrivatePeer != null || currentChannel != null) {
                            // Black arrow on orange for both private and channel modes
                            Color.Black
                        } else if (colorScheme.background == Color.Black) {
                            Color.Black // Black arrow on bright green in dark theme
                        } else {
                            Color.White // White arrow on dark green in light theme
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CommandSuggestionsBox(
    suggestions: List<CommandSuggestion>,
    onSuggestionClick: (CommandSuggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(
                color = colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
//            .border(
//                width = 1.dp,
//                color = colorScheme.onSurface.copy(alpha = 0.2f),
//                shape = RoundedCornerShape(12.dp)
//            )
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        suggestions.forEach { suggestion: CommandSuggestion ->
            CommandSuggestionItem(
                suggestion = suggestion,
                onClick = { onSuggestionClick(suggestion) }
            )
        }
    }
}

@Composable
fun CommandSuggestionItem(
    suggestion: CommandSuggestion,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .background(
                color = colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp),
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Show all aliases together
        val allCommands = if (suggestion.aliases.isNotEmpty()) {
            listOf(suggestion.command) + suggestion.aliases
        } else {
            listOf(suggestion.command)
        }

        Text(
            text = allCommands.joinToString(", "),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = UbuntuFontFamily,
                fontWeight = FontWeight.Bold
            ),
            color = colorScheme.primary,
            fontSize = 13.sp
        )

        // Show syntax if any
        suggestion.syntax?.let { syntax ->
            Text(
                text = syntax,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = UbuntuFontFamily
                ),
                color = colorScheme.onSurface.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
        }

        // Show description
        Text(
            text = suggestion.description,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = UbuntuFontFamily
            ),
            color = colorScheme.onSurface.copy(alpha = 0.7f),
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MentionSuggestionsBox(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = modifier
            .background(
                color = colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
//            .border(
//                width = 1.dp,
//                color = colorScheme.onSurface.copy(alpha = 0.2f),
//                shape = RoundedCornerShape(12.dp)
//            )
//            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        suggestions.forEach { suggestion: String ->
            MentionSuggestionItem(
                suggestion = suggestion,
                onClick = { onSuggestionClick(suggestion) }
            )
        }
    }
}

@Composable
fun MentionSuggestionItem(
    suggestion: String,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(
                color = colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "@$suggestion",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = UbuntuFontFamily,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFFFF9500), // Orange like mentions
            fontSize = 13.sp
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "mention",
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = UbuntuFontFamily
            ),
            color = colorScheme.onSurface.copy(alpha = 0.7f),
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MessageInputPreview() {
    CraysCircleTheme {
        MessageInput(
            value = TextFieldValue("Hello world"),
            onValueChange = {},
            onSend = {},
            selectedPrivatePeer = null,
            currentChannel = null,
            nickname = "user123"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MessageInputPrivateChatPreview() {
    CraysCircleTheme {
        MessageInput(
            value = TextFieldValue(""),
            onValueChange = {},
            onSend = {},
            selectedPrivatePeer = "peer123",
            currentChannel = null,
            nickname = "user123"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommandSuggestionsBoxPreview() {
    CraysCircleTheme {
        val sampleSuggestions = listOf(
            CommandSuggestion(
                command = "/block",
                aliases = listOf("/ban"),
                syntax = "[nickname]",
                description = "Block or list blocked users"
            ),
            CommandSuggestion(
                command = "/join",
                aliases = listOf("/j"),
                syntax = "[channel] [password]",
                description = "Join a channel"
            ),
            CommandSuggestion(
                command = "/help",
                aliases = emptyList(),
                syntax = null,
                description = "Show available commands"
            )
        )
        
        CommandSuggestionsBox(
            suggestions = sampleSuggestions,
            onSuggestionClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MentionSuggestionsBoxPreview() {
    CraysCircleTheme {
        val sampleMentions = listOf("alice", "bob", "charlie", "david")
        
        MentionSuggestionsBox(
            suggestions = sampleMentions,
            onSuggestionClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CommandSuggestionItemPreview() {
    CraysCircleTheme {
        CommandSuggestionItem(
            suggestion = CommandSuggestion(
                command = "/block",
                aliases = listOf("/ban"),
                syntax = "[nickname]",
                description = "Block or list blocked users"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MentionSuggestionItemPreview() {
    CraysCircleTheme {
        MentionSuggestionItem(
            suggestion = "alice",
            onClick = {}
        )
    }
}
