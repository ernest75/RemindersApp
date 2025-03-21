package com.ernestschcneider.feature.reminders.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.models.Reminder
import com.ernestschcneider.models.ReminderType
import com.ernestschcneider.remindersapp.core.testtags.REMINDERS_ITEM_DELETE_ICON
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.RemindersText
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersItem(
    modifier: Modifier = Modifier,
    item: Reminder,
    onItemClicked: (reminderId: String) -> Unit,
    @DrawableRes startDrawableRes: Int = R.drawable.ic_note_24,
    onDeleteItemClicked: (Reminder) -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(64.dp)
            .clickable {
                onItemClicked(item.reminderId)
            }
            .background(AppTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp),
            painter = painterResource(
                id = startDrawableRes
            ),
            contentDescription = stringResource(
                R.string.note_icon
            ),
            tint = AppTheme.colorScheme.contentTint
        )
        RemindersText(
            text = item.reminderTitle,
            modifier = Modifier.padding(start = 48.dp),
            style = AppTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.weight(1F))
        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable { onDeleteItemClicked(item) }
                .testTag(REMINDERS_ITEM_DELETE_ICON + item.reminderId),
            painter = painterResource(id = R.drawable.ic_delete_outline_24),
            contentDescription = stringResource(
                id = R.string.delete_icon
            ),
            tint = AppTheme.colorScheme.contentTint
        )
    }
}

@Composable
@PreviewLightDark
private fun ReminderListItemPreview() {
    AppTheme {
        RemindersItem(
            item = Reminder(
                reminderTitle = "TitleNote",
                reminderContent = "content",
                reminderType = ReminderType.Note
            ),
            onItemClicked = {},
            onDeleteItemClicked = {}
        )
    }
}