package com.ernestschcneider.remindersapp.core.view.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Stable
data class AppColorScheme(
    val backGround: Color,
    val onBackGround: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val primaryContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceBright: Color,
    val onSurfaceBright: Color,
    val secondaryContainer: Color,
    val scrim: Color,
    val surfaceContainerLowest: Color
)

@Stable
 data class AppTypography(
     val titleLarge: TextStyle,
     val titleNormal: TextStyle,
     val paragraph: TextStyle,
     val labelLarge: TextStyle,
     val labelNormal: TextStyle,
     val labelSmall: TextStyle
 )

@Stable
data class AppShape(
    val container: Shape,
    val button: Shape
)

@Stable
data class AppSize(
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        backGround = Color.Unspecified,
        onBackGround = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        surfaceContainerHigh = Color.Unspecified,
        surfaceBright = Color.Unspecified,
        onSurfaceBright = Color.Unspecified,
        secondaryContainer = Color.Unspecified,
        scrim = Color.Unspecified,
        surfaceContainerLowest = Color.Unspecified
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge= TextStyle.Default,
        titleNormal= TextStyle.Default,
        paragraph= TextStyle.Default,
        labelLarge= TextStyle.Default,
        labelNormal= TextStyle.Default,
        labelSmall= TextStyle.Default
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified
    )
}