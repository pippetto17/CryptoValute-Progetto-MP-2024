package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import it.magi.stonks.R
import it.magi.stonks.composables.CoinMarketChart
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun CoinScreen(
    navController: NavController,
    viewModel: StonksViewModel,
    apiKey: String,
    coinId: String,
    currency: String,


    ) {

    viewModel.filterCoinsApiRequest(
        apiKey,
        currency,
        coinId
    )

    val coin = viewModel.getCoinsList().observeAsState().value?.get(0)


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
        containerColor = FormContainerColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = coin?.image,
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
                            text = coin?.name ?: "",
                            color = Color.White,
                            fontFamily = titleFont(),
                            fontSize = 20.sp
                        )
                        Text(
                            text = coin?.symbol?.uppercase() ?: "",
                            color = Color.LightGray,
                            fontStyle = FontStyle.Italic,
                            fontFamily = titleFont(),
                            fontSize = 15.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.Red),
                )
                Spacer(modifier = Modifier.height(30.dp))
                SignButton(
                    onclick = { navController.navigate("buycoin/$coinId") },
                    text = "Add to Wallet"
                )
            }

        }
    }

}


