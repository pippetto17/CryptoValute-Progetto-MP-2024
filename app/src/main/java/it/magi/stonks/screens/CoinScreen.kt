package it.magi.stonks.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import it.magi.stonks.R
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.LineChartGithub
import it.magi.stonks.composables.SignButton
import it.magi.stonks.models.SparkLine
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import okhttp3.internal.toImmutableList


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

    class Coin {
        val name = coin?.name ?: ""
        val symbols = coin?.symbol?.uppercase() ?: ""
        val sparkLineURI = Utilities().sparklineURI(coin?.image)
        val currentPrice = coin?.current_price ?: 0.0f
        val priceInMyCurrency = Utilities().formatPrice(currentPrice)
        val image = coin?.image ?: ""
        val market_cap = coin?.market_cap ?: 0.0f
        val market_cap_rank = coin?.market_cap_rank ?: 0
        val fully_diluted_valuation = coin?.fully_diluted_valuation ?: 0.0f
        val total_volume = coin?.total_volume ?: 0.0f
        val high_24h = coin?.high_24h ?: 0.0f
        val low_24h = coin?.low_24h ?: 0.0f
        val price_change_24h = coin?.price_change_24h ?: 0.0f
        val price_change_percentage_24h = coin?.price_change_percentage_24h ?: 0.0f
        val market_cap_change_24h = coin?.market_cap_change_24h ?: 0.0f
        val market_cap_change_percentage_24h = coin?.market_cap_change_percentage_24h ?: 0.0f
        val circulating_supply = coin?.circulating_supply ?: 0.0f
        val total_supply = coin?.total_supply ?: 0.0f
        val max_supply = coin?.max_supply ?: 0.0f
        val ath = coin?.ath ?: 0.0f
        val ath_change_percentage = coin?.ath_change_percentage ?: 0.0f
        val ath_date = coin?.ath_date ?: ""
        val atl = coin?.atl ?: 0.0f
        val atl_change_percentage = coin?.atl_change_percentage ?: 0.0f
        val atl_date = coin?.atl_date ?: ""
        val roi = coin?.roi ?: ""
        val last_updated = coin?.last_updated ?: ""
        val price_change_percentage_1h_in_currency = coin?.price_change_percentage_1h_in_currency
        val price_change_percentage_24h_in_currency = coin?.price_change_percentage_24h_in_currency
        val price_change_percentage_7d_in_currency = coin?.price_change_percentage_7d_in_currency
        val price_change_percentage_14d_in_currency = coin?.price_change_percentage_14d_in_currency
        val price_change_percentage_30d_in_currency = coin?.price_change_percentage_30d_in_currency
        val price_change_percentage_200d_in_currency =
            coin?.price_change_percentage_200d_in_currency
        val price_change_percentage_1y_in_currency = coin?.price_change_percentage_1y_in_currency
    }

    //API Request della coinMarketChart
    viewModel.coinMarketChartDataById(
        apiKey,
        coinId,
        7
    ) {
        Log.d("CoinMarketChart", "value: $it")
    }

    val chartApiResponse = viewModel.getCoinMarketChart().observeAsState().value
    if (chartApiResponse != null) {
        val chartData = viewModel.formatCoinMarketChartData(chartApiResponse)
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
                text = stringResource(id = R.string.coin_screen_add_to_wallet),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 80.dp, end = 80.dp)
                    .height(40.dp),
                textSize = 15.sp
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
                        modifier = Modifier.fillMaxWidth(),
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
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "name: " + Coin().name,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = Coin().symbols,
                                color = Color.LightGray,
                                fontFamily = titleFont(),
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = Coin().priceInMyCurrency,
                            color = Color.White,
                            fontFamily = titleFont(),
                            fontSize = 20.sp,
                        )
                        Text(
                            text = currency,
                            color = Color.White,
                            fontStyle = FontStyle.Italic,
                            fontFamily = titleFont(),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    if (chartApiResponse != null) {
                        val chartData = viewModel.formatCoinMarketChartData(chartApiResponse)
                        val graphColor = if(Coin().price_change_24h > 0) Color.Green else Color.Red
                        Log.d("CoinMarketChart", "immutablelist: $chartData")

                        Column(modifier = Modifier.fillMaxWidth().requiredHeight(190.dp)){
                            LineChartGithub(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                data = chartData,
                                graphColor = graphColor,
                                showDashedLine = true
                            )
                        }


                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Card {
                        Column() {
                            Text(
                                text = "name: ${coin?.name}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "symbol: ${coin?.symbol}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "marketCap: ${coin?.market_cap}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "marketCapRank: ${coin?.market_cap_rank}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "fullyDilutedValuation: ${coin?.fully_diluted_valuation}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "totalVolume: ${coin?.total_volume}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "high24h: ${coin?.high_24h}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "low24h: ${coin?.low_24h}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "price_change_24h: ${coin?.price_change_24h ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "price_change_percentage_24h: ${coin?.price_change_percentage_24h ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "market_cap_change_24h: ${coin?.market_cap_change_24h ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "market_cap_change_percentage_24h: ${coin?.market_cap_change_percentage_24h ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "circulating_supply: ${coin?.circulating_supply ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "total_supply: ${coin?.total_supply ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "max_supply: ${coin?.max_supply ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "ath: ${coin?.ath ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "ath_change_percentage: ${coin?.ath_change_percentage ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "ath_date: ${coin?.ath_date ?: ""}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "atl: ${coin?.atl ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "atl_change_percentage: ${coin?.atl_change_percentage ?: 0.0f}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "atl_date: ${coin?.atl_date ?: ""}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "roi: ${coin?.roi ?: ""}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "last_updated: ${coin?.last_updated ?: ""}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            coin?.price_change_percentage_1h_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_1h_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_24h_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_24h_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_7d_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_7d_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_14d_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_14d_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_30d_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_30d_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_200d_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_200d_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            coin?.price_change_percentage_1y_in_currency?.let {
                                Text(
                                    text = "price_change_percentage_1y_in_currency: $it",
                                    color = Color.White,
                                    fontSize = 12.sp
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


