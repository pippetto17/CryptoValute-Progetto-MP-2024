package it.magi.stonks.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock

@Composable
fun ExchangeItem(
    modifier: Modifier = Modifier,
    name: String,
    rank: String,
    id: String,
    imageURI: String?,
    price: Float,
    trust: Int,
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = CoinContainerColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Center,
                text = rank,
                fontSize = 15.sp,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(50.dp)
                        .wrapContentHeight()
                ) {
                    AsyncImage(
                        model = imageURI,
                        contentDescription = "exchangeImage",
                        placeholder = painterResource(R.drawable.star_coin),
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Text(
                modifier = Modifier.width(90.dp),
                textAlign = TextAlign.Center,
                text = name,
                fontSize = 13.sp,
                color = Color.White
            )
            Column(
                modifier = Modifier.width(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .width(100.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = price.toString(),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier
                    .width(70.dp),
                text = "$trust/10",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = when (trust) {
                    in 7..10 -> GreenStock
                    in 5..6 -> Color.Yellow
                    else -> RedStock
                }
            )
        }
    }
}