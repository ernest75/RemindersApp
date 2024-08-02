@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.R.string
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.composables.SecondaryButton
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun ReminderCreationDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onCreateNote: () -> Unit,
    onCreateListNotes: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Card(
            //modifier  = modifier.background(AppTheme.colorScheme.primary),
//            colors = CardColors(containerColor = AppTheme.colorScheme.primary,
//                contentColor = AppTheme.colorScheme.onPrimary,
//                disabledContentColor = AppTheme.colorScheme.primary.copy(alpha = 0.25F),
//                disabledContainerColor = AppTheme.colorScheme.onPrimary.copy(alpha = 0.25F))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),

                ) {
                Text(
                    text = stringResource(id = string.new_reminder),
                    style = AppTheme.typography.titleLarge,
                )

                Text(
                    text = stringResource(id = string.new_reminder_explanation),
                    style = AppTheme.typography.paragraph
                )
                OptionItemDialog(
                    modifier = Modifier.padding(top = 16.dp),
                    onClickItem = onCreateNote,
                    drawableRes = R.drawable.ic_note_24,
                    textResId = string.reminder_note,
                    contentDescriptionIconId = string.note_icon
                )
                OptionItemDialog(
                    onClickItem = onCreateListNotes,
                    drawableRes = R.drawable.ic_list_bulleted_24,
                    textResId = string.reminder_list,
                    contentDescriptionIconId = string.list_icon
                )

                SecondaryButton(
                    modifier = Modifier.align(Alignment.End),
                    label = stringResource(
                        id = string.cancel
                    )
                ) {

                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewDialog() {
    ReminderCreationDialog(
        onDismiss = {},
        onCreateNote = {},
        onCreateListNotes = {}
    )
}