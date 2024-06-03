package it.magi.stonks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.utilities.Utilities

@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    imageURI: String?,
    name: String,
    price: String,
    currency: String
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {},
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageURI,
                contentDescription = "coinImage",
                placeholder = painterResource(R.drawable.star_coin),
                modifier = Modifier.size(50.dp)
            )
            Text(text = name)
            Text(text = Utilities().convertDotsToCommas(price) + if (currency == "usd") "$" else "€")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(R.drawable.ic_add_to_wallet),
                    contentDescription = ""
                )
            }
        }
    }
}