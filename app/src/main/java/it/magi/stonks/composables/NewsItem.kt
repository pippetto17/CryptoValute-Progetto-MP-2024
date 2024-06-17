package it.magi.stonks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.magi.stonks.ui.theme.CoinContainerColor

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = CoinContainerColor
        )
    ) {
        Column {
            Text(title, fontSize = 15.sp)
        }
    }
}