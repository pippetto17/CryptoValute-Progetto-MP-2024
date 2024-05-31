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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.googleLoginLabelFont

@Composable
fun SignButton(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = TitleColor,
        contentColor = DarkBgColor
    )
) {
    Button(
        shape = RoundedCornerShape(15.dp),
        colors = colors,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp)
            .height(60.dp),
        onClick = onclick
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 35.sp,
            fontFamily = googleLoginLabelFont()
        )
    }
}