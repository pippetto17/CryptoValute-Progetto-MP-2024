package it.magi.stonks.composables

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import java.text.DecimalFormat

@Composable
fun TrendingCoinItem(
    modifier: Modifier = Modifier,
    rank: String?,
    imageURI: String?,
    symbol: String,
    name: String,
    price: Float,
    marketCap: String,
    priceChangePercentage24h: Float,
    onClick: () -> Unit
) {
    val percentageFormat = DecimalFormat("0.0")
    val priceFormat = DecimalFormat("0.0#######")

    Card(
        modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
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
            if (rank != null) {
                Text(
                    modifier = Modifier.width(30.dp),
                    text = rank,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier.width(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(50.dp)
                        .wrapContentHeight()
                ) {
                    AsyncImage(
                        model = imageURI,
                        contentDescription = "coinImage",
                        placeholder = painterResource(R.drawable.star_coin),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = symbol.uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier.width(120.dp),
                text = name,
                fontSize = 15.sp,
                color = Color.White
            )
            Column(
                modifier = Modifier.width(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "$",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = if (priceFormat.format(price).length > 9) priceFormat.format(price).substring(0, 10)
                        else priceFormat.format(price),
                        fontSize = 14.sp,
                        color = Color.White
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = marketCap,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }

            Text(
                modifier = Modifier.width(50.dp),
                text = if (priceChangePercentage24h >= 0)
                    "▲" + percentageFormat.format(priceChangePercentage24h).toString() + "%"
                else "▼" + percentageFormat.format(priceChangePercentage24h).toString()
                    .substring(1) + "%",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = if (priceChangePercentage24h >= 0) GreenStock else RedStock
            )
        }
    }
}

