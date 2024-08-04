package com.ernestschcneider.remindersapp.core.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    backGround = backgroundDark,
    onBackGround = onBackgroundDark,
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    primaryContainer = primaryContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceBright = surfaceBrightDark,
    onSurfaceBright = onSurfaceDark,
    secondaryContainer = secondaryContainerDark,
    scrim = scrimDark,
    surfaceContainerLowest = surfaceContainerLowestDark
)

private val lightColorScheme = AppColorScheme(
    backGround = backgroundLight,
    onBackGround =  onBackgroundLight,
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    primaryContainer = primaryContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceBright = surfaceBrightLight,
    onSurfaceBright = onSurfaceLight,
    secondaryContainer = secondaryContainerLight,
    scrim = scrimLight,
    surfaceContainerLowest = surfaceContainerLowestLight
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    paragraph = TextStyle(
        fontFamily = IbmPlexSans,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = IbmPlexSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    labelNormal = TextStyle(
        fontFamily = IbmPlexSans,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = IbmPlexSans,
        fontSize = 12.sp
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}

