package com.ernestschcneider.feature.reminderlist.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme

@Composable
fun AddReminder(
    modifier: Modifier = Modifier,
    onAddReminderClicked: ()-> Unit
) {
    Row(modifier = modifier
        .padding(top = 32.dp)
        .clickable {}
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icc_add_circle_24),
            contentDescription = stringResource(id = R.string.add_reminder_first_in_list_icon),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
                .size(24.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = 48.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.add_reminder),
            style = AppTheme.typography.labelLarge
        )
    }
}