package it.magi.stonks.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@Composable
fun titleFont(): FontFamily {
    val assets = LocalContext.current.assets
    return FontFamily(
        Font("Heavitas.ttf", assets)
    )
}

@Composable
fun walletFont(): FontFamily {
    val assets = LocalContext.current.assets
    return FontFamily(
        Font("OCR-a.ttf", assets)
    )
}
