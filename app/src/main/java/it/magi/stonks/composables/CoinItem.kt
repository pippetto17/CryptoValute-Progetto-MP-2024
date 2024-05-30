package it.magi.stonks.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import it.magi.stonks.R

@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    imageURI: String?,
    name: String,
    symbol: String,
    price: String
) {
    Card(
        Modifier.fillMaxWidth()
    ) {
        Row {
            AsyncImage(
                model = imageURI,
                contentDescription = "coinImage",
                placeholder = painterResource(R.drawable.star_coin),
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = name)
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = "${price}$")
        }
    }
}