package it.magi.stonks.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.titleFont

@Composable
fun LoginDivisor(modifier: Modifier = Modifier, isForm: Boolean = true) {
    if (isForm) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = modifier.height(10.dp))
            Box(
                modifier = modifier
                    .height(1.dp)
                    .fillMaxWidth(0.95f)
                    .background(Color(0xFF364261))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = modifier.height(10.dp))
        }

    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 40.dp,
                    end = 40.dp,
                    top = 5.dp,
                    bottom = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .height(1.dp)
                    .background(Color.LightGray)
                    .weight(1f)
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = stringResource(R.string.signin_divisor),
                color = Color.LightGray,
                fontFamily = titleFont(),
                fontSize = 12.sp
            )
            Box(
                Modifier
                    .height(1.dp)
                    .background(Color.LightGray)
                    .weight(1f)
            )
        }
    }
}