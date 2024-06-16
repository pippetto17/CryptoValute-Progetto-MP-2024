package it.magi.stonks.composables

import android.os.Build.VERSION.SDK_INT
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
import it.magi.stonks.utilities.Utilities
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
                fontSize = 14.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier.width(120.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = Utilities().formatPrice(floorPriceInNativeCurrency),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = nativeCurrency.uppercase(),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier.width(80.dp),
                text = Utilities().percentageFormat(floorPrice24HPercentage),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = if (floorPrice24HPercentage >= 0) GreenStock else RedStock
            )
        }
    }
}

