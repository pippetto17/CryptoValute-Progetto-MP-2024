package it.magi.stonks.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.utilities.Utilities

@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    prefCurrency: String,
    rank: String,
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
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(20.dp).background(Color.Red),
                text = rank,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Box(
                modifier = Modifier.width(80.dp).background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.size(50.dp)
                ) {
                    AsyncImage(
                        model = imageURI,
                        contentDescription = "coinImage",
                        placeholder = painterResource(R.drawable.star_coin),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = symbol.uppercase(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            Row(
                modifier = Modifier.width(120.dp).background(Color.Red),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = price.toString(),
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = prefCurrency.uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            IconButton(
                modifier = Modifier.width(30.dp).background(Color.Red),
                onClick = onAddClick) {
                Icon(
                    painterResource(R.drawable.ic_add_to_wallet),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}