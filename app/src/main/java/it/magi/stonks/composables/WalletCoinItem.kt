package it.magi.stonks.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.ui.theme.CoinContainerColor
import java.text.DecimalFormat

@Composable
fun WalletCoinItem(
    modifier: Modifier = Modifier,
    prefCurrency: String,
    id: String,
    imageURI: String?,
    name: String,
    symbol: String,
    amount:String,
    price: Float,

    onClick: () -> Unit
) {
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
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(80.dp),
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
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            Text(text = name,color = Color.White,fontSize = 15.sp,)
            Text(text = amount,color = Color.White,fontSize = 15.sp,)
            Row(
                modifier = Modifier
                    .width(120.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (priceFormat.format(price).length > 8) priceFormat.format(price)
                        .substring(0, 9) else priceFormat.format(price),
                    fontSize = 15.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = prefCurrency.uppercase(),
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White
                )
            }
        }
    }
}