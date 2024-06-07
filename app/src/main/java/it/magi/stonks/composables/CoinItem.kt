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
fun CoinItem(
    modifier: Modifier = Modifier,
    prefCurrency: String,
    rank: String,
    id: String,
    imageURI: String?,
    name: String,
    symbol: String,
    price: Float,
    priceChangePercentage24h: Float,
    onClick: () -> Unit
) {
    val percentageFormat = DecimalFormat("0.0")
    val priceFormat = DecimalFormat("0.000")

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
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(30.dp),
                text = rank,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
            Box(
                modifier = Modifier.width(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.width(50.dp).wrapContentHeight()
                ) {
                    AsyncImage(
                        model = imageURI,
                        contentDescription = "coinImage",
                        placeholder = painterResource(R.drawable.star_coin),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = symbol.uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .width(120.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = priceFormat.format(price).toString(),
                    fontSize = 12.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = prefCurrency.uppercase(),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier.width(70.dp),
                text = if (priceChangePercentage24h >= 0)
                    "▲" + percentageFormat.format(priceChangePercentage24h).toString() + "%"
                else "▼" + percentageFormat.format(priceChangePercentage24h).toString().substring(1) + "%",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = if (priceChangePercentage24h >= 0) GreenStock else RedStock
            )
        }
    }
}