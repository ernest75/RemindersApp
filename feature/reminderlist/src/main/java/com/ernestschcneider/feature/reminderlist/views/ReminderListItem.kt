package com.ernestschcneider.feature.reminderlist.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ernestschcneider.feature.reminderlist.ReminderItem
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersListItem(
    modifier: Modifier = Modifier,
    item: ReminderItem,
    editReminder: (ReminderItem) -> Unit,
    deleteReminder: (String) -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(64.dp)
                .background(AppTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val textLimited = if (item.text.length > 20) {
                item.text.subSequence(0,20).toString() + "..."
            } else {
                item.text
            }
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = textLimited,
                style = AppTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_pencil_24),
                contentDescription = stringResource(id = R.string.edit_reminder),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
                    .size(16.dp)
                    .clickable { editReminder(item) }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_outline_24),
                contentDescription = stringResource(id = R.string.delete_reminder),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
                    .size(16.dp)
                    .clickable { deleteReminder(item.text) }
            )
        }
    }

}

@Composable
@PreviewLightDark
private fun ReminderListItemPreview() {
    AppTheme {
        RemindersListItem(
            item = ReminderItem(),
            editReminder = {},
            deleteReminder = {}
        )
    }
}