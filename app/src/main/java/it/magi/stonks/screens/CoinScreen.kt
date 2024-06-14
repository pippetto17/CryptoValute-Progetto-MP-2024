package it.magi.stonks.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import java.text.DecimalFormat

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    apiKey: String,
    coinId: String,
    currency: String,
) {

    //API Request dei dati della coin
    viewModel.filterCoinsApiRequest(
        apiKey,
        currency,
        coinId
    )
    val coin = viewModel.getCoinsList().observeAsState().value?.get(0)

    val percentageFormat = DecimalFormat("0.0")
    val priceFormat = DecimalFormat("0.0#######")

    val regex = Regex("/(\\d+)/")

    val matchResult = regex.find(coin?.image ?: "")
    val currencyImageNumber = matchResult?.groupValues?.get(1)?.toIntOrNull()

    //API Request della coinMarketChart
    viewModel.coinMarketChartDataById(
        apiKey,
        coinId,
        currency,
        7
    )

    val chartData = viewModel.getCoinMarketChart().observeAsState().value?.prices

    class Coin{
        val name = coin?.name ?: ""
        val symbols = coin?.symbol?.uppercase() ?: ""
        val sparkLineURI = "https://www.coingecko.com/coins/$currencyImageNumber/sparkline.svg"
        val current_price = coin?.current_price ?: 0.0f
        val priceInMyCurrency =
            if (priceFormat.format(current_price).length > 8) priceFormat.format(
                current_price
            ).substring(0, 9) else priceFormat.format(current_price)
        val image = coin?.image ?: ""
        val market_cap = coin?.market_cap ?: 0.0f
        val market_cap_rank = coin?.market_cap_rank ?: 0.0f
        val total_volume = coin?.total_volume ?: 0.0f
        val high_24h = coin?.high_24h ?: 0.0f
        val low_24h = coin?.low_24h ?: 0.0f
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false,
                viewModel = viewModel
            )
        },
        floatingActionButton = {
            SignButton(
                onclick = { navController.navigate("buycoin/$coinId") },
                text = "Add to Wallet"
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = FormContainerColor
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.5f),
                        ) {
                            AsyncImage(
                                model = Coin().image,
                                contentDescription = "",
                                placeholder = painterResource(R.drawable.star_coin),
                                modifier = Modifier.size(80.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 10.dp)
                            ) {
                                Text(
                                    text = Coin().name,
                                    color = Color.White,
                                    fontFamily = titleFont(),
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = Coin().symbols,
                                    color = Color.LightGray,
                                    fontFamily = titleFont(),
                                    fontSize = 10.sp
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(0.5f),
                        ) {
                            Text(
                                text = Coin().priceInMyCurrency,
                                color = Color.White,
                                fontSize = 10.sp,
                            )
                            Text(
                                text = currency,
                                color = Color.White,
                                fontStyle = FontStyle.Italic,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, RedStock),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkBgColor
                        ),
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Coin().sparkLineURI)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = "sparkLine",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, GreenStock),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkBgColor
                        ),
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = "Name",
                                    modifier = Modifier.fillMaxWidth(0.3f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                                TextField(
                                    value = Utilities().getAccountName().uppercase(),
                                    modifier = Modifier.fillMaxWidth(1f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.End,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = "Surname",
                                    modifier = Modifier.fillMaxWidth(0.4f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                                TextField(
                                    value = Utilities().getAccountSurname().uppercase(),
                                    modifier = Modifier.fillMaxWidth(1f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.End,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = "Email",
                                    modifier = Modifier.fillMaxWidth(0.25f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                                TextField(
                                    value = "gendoikari01@nerv.jp",
                                    modifier = Modifier.fillMaxWidth(1f),
                                    onValueChange = {},
                                    readOnly = true,
                                    enabled = false,
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.End,
                                        fontSize = 14.sp
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(120.dp))
                }

            }
        }
    }

}


