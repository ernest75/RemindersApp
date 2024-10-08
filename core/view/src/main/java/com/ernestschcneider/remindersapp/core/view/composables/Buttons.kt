package com.ernestschcneider.remindersapp.core.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.core.view.theme.AppTheme
import com.ernestschcneider.remindersapp.core.view.theme.PreviewLightDark


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isVisible: Boolean = true
) {
    if (isVisible) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary
            ),
            shape = AppTheme.shape.button
        ) {
            Text(text = label, style = AppTheme.typography.labelLarge)
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isVisible: Boolean = true
) {
    if (isVisible) {
        OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = AppTheme.colorScheme.secondary,
                contentColor = AppTheme.colorScheme.onSecondary
            ),
            shape = AppTheme.shape.button,
            border = BorderStroke(1.dp, AppTheme.colorScheme.onSecondary)
        ) {
            Text(text = label, style = AppTheme.typography.labelLarge)
        }
    }

}

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isExpanded: Boolean = false
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.surfaceBright,
        contentColor = AppTheme.colorScheme.onSurfaceBright,
        text = { Text(text = label, style = AppTheme.typography.labelLarge) },
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, label) },
        expanded = isExpanded
    )
}

@PreviewLightDark
@Composable
private fun PreviewPrimaryButton() {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(AppTheme.size.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
        ) {
            PrimaryButton(
                label = "Primary",
                onClick = {}
            )
            SecondaryButton(
                label = "Secondary",
                onClick = {}
            )
            FloatingActionButton(label = "Add Reminder", onClick = {})
        }
    }
}
