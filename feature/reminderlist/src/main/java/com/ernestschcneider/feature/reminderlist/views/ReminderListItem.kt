package com.ernestschcneider.feature.reminderlist.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ernestschcneider.models.ReminderListItem
import com.ernestschcneider.remindersapp.core.testtags.REMINDERS_ITEM_DELETE_ICON
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersListItem(
    modifier: Modifier = Modifier,
    item: ReminderListItem,
    editReminder: (ReminderListItem) -> Unit,
    deleteReminder: (ReminderListItem) -> Unit,
    crossReminder: (ReminderListItem) -> Unit
) {
    Column {
        val alpha = if (item.isCrossed) {
            .5f
        } else {
            1f
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
               crossReminder(item)
            },
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.alpha(alpha)) {
                Row(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(AppTheme.colorScheme.primaryContainer),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val textLimited = if (item.text.length > 20) {
                        item.text.subSequence(0, 20).toString() + "..."
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
                        color = AppTheme.colorScheme.onBackGround
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
                            .clickable { editReminder(item) },
                        tint = AppTheme.colorScheme.contentTint
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_outline_24),
                        contentDescription = stringResource(id = R.string.delete_reminder),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                            .size(16.dp)
                            .clickable { deleteReminder(item) }
                            .testTag(REMINDERS_ITEM_DELETE_ICON + item.position),
                        tint = AppTheme.colorScheme.contentTint
                    )
                }

            }
            if (item.isCrossed) {
                Box {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 2.dp)
                }

            }
        }
    }
}

@Composable
@PreviewLightDark
private fun ReminderListItemPreview() {
    AppTheme {
        RemindersListItem(
            item = ReminderListItem(text = "hello"),
            editReminder = {},
            deleteReminder = {},
            crossReminder = {}
        )
    }
}
