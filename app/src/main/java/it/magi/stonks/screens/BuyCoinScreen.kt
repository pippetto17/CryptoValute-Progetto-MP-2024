package it.magi.stonks.screens

import android.app.Application
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CustomField
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.SignField
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.viewmodels.HomeViewModel
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun BuyCoinScreen(
    navController: NavController,
    viewModel: WalletViewModel,
    coinId: String
) {
    val application = LocalContext.current.applicationContext as Application
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CustomTopAppBar(
                navController = navController,
                isHome = false,
                viewModel = HomeViewModel(application)
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
                text = "Prezzo per valuta"
            )
            CustomField(
                value = coinId,
                onValueChange = { /*TODO*/ }
            )
            Text(
                text = "Quantità"
            )
            CustomField(
                value = "prova",
                onValueChange = { /*TODO*/ }
            )
            Text(
                text = "Totale speso"
            )
            CustomField(
                value = "prova",
                onValueChange = { /*TODO*/ }
            )
            Text(
                text = "Data"
            )
            CustomField(
                value = "prova",
                onValueChange = { /*TODO*/ }
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