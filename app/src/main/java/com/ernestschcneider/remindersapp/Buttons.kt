package com.ernestschcneider.remindersapp

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ernestschcneider.remindersapp.ui.theme.AppTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
){
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

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppTheme.colorScheme.secondary,
            contentColor = AppTheme.colorScheme.onSecondary
        ),
        shape = AppTheme.shape.button,
        border = BorderStroke(1.dp, AppTheme.colorScheme.onSecondary )
    ) {
        Text(text = label, style = AppTheme.typography.labelLarge)
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewLightDark

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
                onClick= {}
            )
        }
    }
}
