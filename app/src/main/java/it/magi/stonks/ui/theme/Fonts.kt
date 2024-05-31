package it.magi.stonks.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@Composable
fun titleFont(): FontFamily {
    val assets = LocalContext.current.assets
    return FontFamily(
        Font("origintech.ttf", assets)
    )
}

@Composable
fun googleLoginLabelFont():FontFamily{
    val assets = LocalContext.current.assets
    return FontFamily(
        Font("SonicSurge.ttf", assets)
    )
}