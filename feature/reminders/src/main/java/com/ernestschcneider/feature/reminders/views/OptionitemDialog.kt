package com.ernestschcneider.feature.reminders.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun OptionItemDialog(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    @DrawableRes drawableRes: Int,
    @StringRes textResId: Int,
    @StringRes contentDescriptionIconId: Int
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClickItem)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 8.dp),
                painter = painterResource(
                    id = drawableRes
                ),
                contentDescription = stringResource(id = contentDescriptionIconId)
            )

            Text(
                text = stringResource(textResId),
                modifier = Modifier.padding(start = 24.dp),
                style = AppTheme.typography.labelLarge
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@PreviewLightDark
@Composable
fun OptionItemDialogPreview() {
    OptionItemDialog(
        onClickItem = {},
        drawableRes = R.drawable.ic_note_24,
        textResId = R.string.reminder_note,
        contentDescriptionIconId = R.string.note_icon
    )
}