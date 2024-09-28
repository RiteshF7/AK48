package com.trex.laxmiemi.utils

// Type.kt

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.trex.laxmiemi.R

object AppFont {
    val robotoFonts = FontFamily(
        Font(R.font.roboto_regular),
        Font(R.font.roboto_italic, style = FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_mediumitalic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_blackitalic, FontWeight.Bold, style = FontStyle.Italic)
    )
}

private val defaultTypography = Typography()

val AppTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.robotoFonts),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.robotoFonts),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.robotoFonts),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.robotoFonts),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.robotoFonts),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.robotoFonts),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.robotoFonts),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.robotoFonts),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.robotoFonts),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.robotoFonts),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.robotoFonts),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.robotoFonts),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.robotoFonts),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.robotoFonts),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.robotoFonts)
)