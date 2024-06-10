package it.magi.stonks.screen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CustomField
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.viewmodels.StonksViewModel
import it.magi.stonks.viewmodels.WalletViewModel


@Composable
fun BuyCoinScreen(
    navController: NavController,
    viewModel: WalletViewModel,
    coinId: String,
    currency: String
) {
    val application = LocalContext.current.applicationContext as Application
    viewModel.coinPriceApiRequest(
        apiKey,
        coinId,
        currency.lowercase(),
        viewModel.printResultCallback
    )

    val coinPriceState = viewModel.coinPrice.collectAsState()
    val quantityState = viewModel.quantity.collectAsState()
    val totalSpentState = viewModel.totalSpent.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false,
                viewModel = StonksViewModel(application)
            )
        },
        containerColor = FormContainerColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Text(
                text = "Valuta",
                color = Color.White
            )
            CustomField(
                value = coinId,
                onValueChange = { /*TODO*/ }
            )
            Text(
                text = "Quantità", color = Color.White
            )
            CustomField(
                value = quantityState.value.toString(),
                onValueChange = {
                    viewModel._quantity.value = it
                    viewModel._totalSpent.value = (it.toFloat() * coinPriceState.value.toFloat()).toString()
                }
            )
            Text(
                text = "Totale speso"
            )
            CustomField(
                value = totalSpentState.value.toString(),
                onValueChange = {
                    viewModel._totalSpent.value = it
                    viewModel._quantity.value = (it.toFloat() / coinPriceState.value.toFloat()).toString()
                },
            )

            Text(
                text = "Valore per azione ${coinPriceState.value}$currency",
                color = Color.White
            )

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            ) {
                SignButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 40.dp, end = 10.dp),
                    onclick = {},
                    text = "cancel",
                    textSize = 15.sp
                )
                SignButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 10.dp, end = 40.dp),
                    onclick = {},
                    text = "buy",
                    textSize = 15.sp,
                    colors = ButtonDefaults.buttonColors(containerColor = RedStock)
                )
            }
        }
    }
}