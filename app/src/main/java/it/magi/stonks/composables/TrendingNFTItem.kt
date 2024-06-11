package it.magi.stonks.composables

import android.os.Build.VERSION.SDK_INT
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
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.ui.theme.CoinContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import java.text.DecimalFormat

@Composable
fun TrendingNFTItem(
    modifier: Modifier = Modifier,
    thumb: String,
    name: String,
    nativeCurrency: String,
    floorPriceInNativeCurrency: Float,
    floorPrice24HPercentage: Float,
    id: String
) {
    val percentageFormat = DecimalFormat("0.0")
    val priceFormat = DecimalFormat("0.0#######")

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
                    if (thumb.contains("jpg")) {
                        AsyncImage(
                            model = thumb,
                            contentDescription = "nftImage",
                            placeholder = painterResource(R.drawable.star_coin),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    if (thumb.contains("svg")) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(thumb)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = "nftImage",
                            placeholder = painterResource(R.drawable.star_coin),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    if (thumb.contains("png")) {
                        AsyncImage(
                            model = thumb,
                            contentDescription = "nftImage",
                            placeholder = painterResource(R.drawable.star_coin),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    if (thumb.contains("gif")) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(thumb)
                                .decoderFactory(
                                    if (SDK_INT >= 28) {
                                        ImageDecoderDecoder.Factory()
                                    } else {
                                        GifDecoder.Factory()
                                    }
                                )
                                .build(),
                            contentDescription = "nftGif",
                            placeholder = painterResource(R.drawable.star_coin),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.width(120.dp),
                text = name,
                fontSize = 15.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier.width(100.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (priceFormat.format(floorPriceInNativeCurrency).length > 8) priceFormat.format(
                        floorPriceInNativeCurrency
                    )
                        .substring(0, 9) else priceFormat.format(floorPriceInNativeCurrency),
                    fontSize = 12.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = nativeCurrency,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier.width(80.dp),
                text = if (floorPrice24HPercentage >= 0)
                    "▲" + percentageFormat.format(floorPrice24HPercentage).toString() + "%"
                else "▼" + percentageFormat.format(floorPrice24HPercentage).toString()
                    .substring(1) + "%",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = if (floorPrice24HPercentage >= 0) GreenStock else RedStock
            )
        }
    }
}

