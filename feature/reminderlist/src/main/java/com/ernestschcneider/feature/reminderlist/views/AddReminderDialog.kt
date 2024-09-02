package com.ernestschcneider.feature.reminderlist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.composables.PrimaryButton
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderDialog(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onDismiss: () -> Unit,
    onFirsReminderAdded: (String) -> Unit,
    isFirstReminder: Boolean
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
            // WorkAround remove when https://issuetracker.google.com/issues/204502668?pli=1 is fixed
            // Update should be fixed in compose 1.7
            val windowInfo = LocalWindowInfo.current
            LaunchedEffect(windowInfo) {
                snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
                    if (isWindowFocused) {
                        focusRequester.requestFocus()
                    }
                }
            }
            val focusManager = LocalFocusManager.current
            val text = remember { mutableStateOf("") }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),

                ) {
                Text(
                    text = stringResource(id = R.string.add_reminder),
                    style = AppTheme.typography.titleLarge,
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    textStyle = AppTheme.typography.labelLarge,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.add_reminder),
                            color = AppTheme.colorScheme.secondary
                        )
                    },
                    value = text.value,
                    onValueChange = {
                        text.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        // TODO hide keyboard??
                    })
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(0.5F))
                    PrimaryButton(
                        modifier = Modifier.padding(end = 4.dp),
                        label = stringResource(id = R.string.save),
                        onClick = {
                            if (isFirstReminder) {
                                onFirsReminderAdded(text.value)
                            } else {
                                onFirsReminderAdded(text.value)
                            }
                            onDismiss()
                        }
                    )
                    PrimaryButton(
                        label = stringResource(id = R.string.cancel),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewDialog() {
    AddReminderDialog(
        onDismiss = {},
        focusRequester = FocusRequester(),
        onFirsReminderAdded = {},
        isFirstReminder = false
    )
}