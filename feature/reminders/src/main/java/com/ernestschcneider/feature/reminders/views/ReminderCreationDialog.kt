@file:OptIn(ExperimentalMaterial3Api::class)

package com.ernestschcneider.feature.reminders.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.R.string
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun ReminderCreationDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onCreateReminder: () -> Unit,
    onCreateListReminder: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier.padding(16.dp),
            colors = CardColors(
                containerColor = AppTheme.colorScheme.secondaryContainer,
                contentColor = AppTheme.colorScheme.scrim,
                disabledContentColor = AppTheme.colorScheme.primary.copy(alpha = 0.25F),
                disabledContainerColor = AppTheme.colorScheme.onPrimary.copy(alpha = 0.25F)
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                )
                {
                    Text(
                        text = stringResource(id = string.new_reminder),
                        style = AppTheme.typography.titleLarge,
                    )

                    Text(
                        text = stringResource(id = string.new_reminder_explanation),
                        style = AppTheme.typography.paragraph
                    )
                }

                OptionItemDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(24.dp),
                    drawableRes = R.drawable.ic_note_24,
                    textResId = string.reminder_note,
                    contentDescriptionIconId = string.note_icon,
                    onClickItem = onCreateReminder
                )
                OptionItemDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(24.dp),
                    drawableRes = R.drawable.ic_list_bulleted_24,
                    textResId = string.reminder_list,
                    contentDescriptionIconId = string.list_icon,
                    onClickItem = onCreateListReminder
                )
                PrimaryButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp),
                    label = stringResource(id = string.cancel),
                    onClick = onDismiss
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewDialog() {
    AppTheme {
        ReminderCreationDialog(
            onDismiss = {},
            onCreateReminder = {},
            onCreateListReminder = {}
        )
    }
}