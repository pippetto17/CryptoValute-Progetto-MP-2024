package it.magi.stonks.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun WalletCard(
    walletName: String,
    gradientColors: List<Color>,
    tabState: Int,
    viewModel: WalletViewModel,
    database: FirebaseDatabase,
    walletList: List<String>,
    it: Int,
    apiKey: String,
    currency: String,
    titleFont: FontFamily,
    walletFont: FontFamily,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var coinsNumber by remember { mutableStateOf(0) }
    var coinsAmount by remember { mutableStateOf(0f) }
    var totalValue by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .width(400.dp)
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    onclick()
                }
            ) {
                LaunchedEffect(key1 = true) {
                    viewModel.getWalletCoinsList(
                        database,
                        walletList[it]
                    ) { walletDetails ->
                        walletDetails.forEach { (crypto, amount) ->
                            coinsNumber += 1
                            coinsAmount += amount.toFloat()
                            viewModel.coinPriceApiRequest(
                                apiKey,
                                crypto.lowercase(),
                                currency
                            ) { pricePerValue ->
                                totalValue += pricePerValue * amount.toFloat()
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Wallet Name
                    Text(
                        text = walletName.uppercase(),
                        style = TextStyle(
                            fontSize = 30.sp,
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(5f, 5f),
                                blurRadius = 3f
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = walletFont
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // Details
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp)
                    ) {
                        // Total Value
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = Utilities().formatExponentialPriceToReadable(totalValue.toString()),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = walletFont
                                )
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = currency.uppercase(),
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = walletFont
                                )
                            )
                        }
                        // Coins Number
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.wallet_screen_coins),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = walletFont
                                )
                            )
                            Text(
                                text = coinsNumber.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = walletFont
                                )
                            )
                        }
                        // Stocks Number
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.wallet_screen_stocks),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = walletFont
                                )
                            )
                            Text(
                                text = coinsAmount.toString(),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    shadow = Shadow(
                                        color = Color.Gray,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 3f
                                    ),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = walletFont
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}