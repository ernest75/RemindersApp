package com.ernestschcneider.remindersapp.core.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.R
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark

@Composable
fun RemindersTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onTitleUpdate: (newValue: String) -> Unit
) {
    val title = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(AppTheme.colorScheme.surfaceContainerLowest)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .clickable { onNavigateUp() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = stringResource(id = R.string.back_arrow_icon),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Center)
                        .size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                placeholder = { Text("Note title") },
                value = title.value,
                onValueChange = {
                    title.value = it
                    onTitleUpdate(it)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ReminderTopAppBar() {
    RemindersTopAppBar(
        onNavigateUp = {},
        onTitleUpdate = {}
    )
}
