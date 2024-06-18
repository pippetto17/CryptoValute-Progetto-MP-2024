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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.utilities.Utilities

@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    prefCurrency: String,
    rank: String,
    id: String,
    imageURI: String?,
    symbol: String,
    price: Float,
    marketCap: Float,
    priceChangePercentage24h: Float,

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
                        contentDescription = "coinImage",
                        placeholder = painterResource(R.drawable.star_coin),
                        modifier = Modifier.size(25.dp)
                    )
                    Text(
                        text = symbol.uppercase(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
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
                        text = Utilities().formatItemPrice(price),
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
                Row(
                    modifier = Modifier
                        .width(100.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = Utilities().formatItemPrice(marketCap),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "$",
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Utilities().sparklineURI(imageURI))
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "sparkLine",
                modifier = Modifier
                    .width(100.dp)
                    .padding(start = 10.dp, end = 10.dp)
            )
            Text(
                modifier = Modifier
                    .width(70.dp),
                text = Utilities().percentageFormat(priceChangePercentage24h),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = if (priceChangePercentage24h >= 0) GreenStock else RedStock
            )
        }
    }
}

