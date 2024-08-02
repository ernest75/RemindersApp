package com.ernestschcneider.feature.reminders.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun OptionItemDialog(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    @DrawableRes drawableRes: Int,
    @StringRes textResId: Int,
    @StringRes contentDescriptionIconId: Int
) {
    Column(
        modifier = modifier.clickable(onClick = onClickItem)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(16.dp)
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
                modifier = Modifier.padding(start = 24.dp)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}