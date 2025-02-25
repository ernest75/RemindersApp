package com.ernestschcneider.remindersapp.core.view.composables

import androidx.annotation.StringRes
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersText(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    style: TextStyle = LocalTextStyle.current,
    color: Color = AppTheme.colorScheme.contentTint
){
    Text(
        modifier = modifier,
        text = stringResource(id = textId),
        style = style,
        color = color
    )
}

@PreviewLightDark
@Composable
fun RemindersTextPreview() {
    AppTheme{
        RemindersText(
            textId = R.string.close_icon,
            style = AppTheme.typography.titleLarge
        )
    }

}