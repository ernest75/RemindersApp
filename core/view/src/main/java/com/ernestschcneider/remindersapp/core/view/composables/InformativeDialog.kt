package com.ernestschcneider.remindersapp.core.view.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.testtags.*
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformativeDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    @StringRes titleId: Int,
    @StringRes explanationId: Int
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .padding(16.dp)
                .testTag(INFORMATIVE_DIALOG),
            colors = CardColors(
                containerColor = AppTheme.colorScheme.secondaryContainer,
                contentColor = AppTheme.colorScheme.scrim,
                disabledContentColor = AppTheme.colorScheme.primary.copy(alpha = 0.25F),
                disabledContainerColor = AppTheme.colorScheme.onPrimary.copy(alpha = 0.25F)
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),

                ) {
                Text(
                    text = stringResource(id = titleId),
                    style = AppTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(id = explanationId),
                    style = AppTheme.typography.paragraph
                )
                PrimaryButton(
                    modifier = Modifier.align(Alignment.End),
                    label = stringResource(id = R.string.ok),
                    onClick = onDismiss
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewInformativeDialog() {
    InformativeDialog(
        onDismiss = {},
        titleId = R.string.close_icon,
        explanationId = R.string.app_name
    )
}