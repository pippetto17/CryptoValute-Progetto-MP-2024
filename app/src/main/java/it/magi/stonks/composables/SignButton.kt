package it.magi.stonks.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.sign_button_size
import it.magi.stonks.ui.theme.titleFont

@Composable
fun SignButton(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 40.dp, end = 40.dp)
        .height(60.dp),
    onclick: () -> Unit,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = TitleColor,
        contentColor = DarkBgColor
    ),
    textSize: TextUnit = sign_button_size
) {
    Button(
        shape = RoundedCornerShape(15.dp),
        colors = colors,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier,
        onClick = onclick
    ) {
        Text(
            text = text.uppercase(),
            fontSize = textSize,
            fontFamily = titleFont()
        )
    }
}