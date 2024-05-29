package it.magi.stonks.objects

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.magi.stonks.R
import it.magi.stonks.ui.theme.BgColor
import it.magi.stonks.ui.theme.DarkBgColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBgColor,
            titleContentColor = Color.White
        )
    )
}