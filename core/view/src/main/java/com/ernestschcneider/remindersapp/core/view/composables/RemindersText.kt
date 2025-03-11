package com.ernestschcneider.remindersapp.core.view.composables

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    color: Color = AppTheme.colorScheme.contentTint,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
){
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@PreviewLightDark
@Composable
fun RemindersTextPreview() {
    AppTheme{
        RemindersText(
            text = "text",
            style = AppTheme.typography.titleLarge
        )
    }

}