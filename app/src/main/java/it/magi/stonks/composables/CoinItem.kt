package it.magi.stonks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.utilities.Utilities

@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    id: String,
    imageURI: String?,
    name: String,
    symbol: String,
    price: String,
    onClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = id)
            Column {
                AsyncImage(
                    model = imageURI,
                    contentDescription = "coinImage",
                    placeholder = painterResource(R.drawable.star_coin),
                    modifier = Modifier.size(15.dp)
                )
                Text(text = symbol, fontSize = 10.sp)
            }
            Text(text = Utilities().convertDotsToCommas(price)+" $")
            IconButton(onClick = onAddClick) {
                Icon(
                    painterResource(R.drawable.ic_add_to_wallet),
                    contentDescription = ""
                )
            }
        }
    }
}