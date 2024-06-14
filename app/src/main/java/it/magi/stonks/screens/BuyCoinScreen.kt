package it.magi.stonks.screen

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.activities.apiKey
import it.magi.stonks.composables.CustomTopAppBar
import it.magi.stonks.composables.NewWalletDialog
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.DecimalFormatter
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.StonksViewModel
import it.magi.stonks.viewmodels.WalletViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyCoinScreen(
    navController: NavController,
    viewModel: WalletViewModel,
    coinId: String,
    currency: String
) {
    val application = LocalContext.current.applicationContext as Application
    val context = LocalContext.current
    viewModel.coinPriceApiRequest(
        apiKey,
        coinId,
        currency.lowercase(),
        viewModel.printResultCallback
    )

    viewModel.getWalletsList(
        database = FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/"),
        viewModel.returnWalletListCallback
    )
    val sheetState = rememberModalBottomSheetState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var showNewWalletDialog by remember { mutableStateOf(false) }
    var showSuccesAnimation by remember { mutableStateOf(false) }

    var selectedWallet by remember { mutableStateOf(0) }


    val walletState = viewModel.wallets.collectAsState()
    val coinPriceState = viewModel.coinPrice.collectAsState()
    val price = Utilities().convertScientificToDecimal(coinPriceState.value.toString())
    val quantityState = viewModel.quantity.collectAsState()
    val totalSpentState = viewModel.totalSpent.collectAsState()

    val decimalFormatter = DecimalFormatter()

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
        if (showSuccesAnimation) {
            val composition by rememberLottieComposition(spec=LottieCompositionSpec.RawRes(R.raw.added_to_wallet_animation))
            Dialog (onDismissRequest = {showSuccesAnimation=false}){
                LottieAnimation(composition = composition,iterations = 1)

            }
        }
        if (showNewWalletDialog) {
            NewWalletDialog(
                onDismissRequest = { showNewWalletDialog = false },
                viewModel = viewModel
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Choose a wallet to add this coin".uppercase(),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 20.sp,
                        fontFamily = titleFont(),
                        textAlign = TextAlign.Center
                    )
                    Text(text=walletState.value[selectedWallet], color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        items(walletState.value.size) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                                .clickable {
                                    selectedWallet = it
                                }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = walletState.value[it])
                                    if (selectedWallet == it) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_checked_wallet),
                                            contentDescription = null,
                                            Modifier.size(20.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (walletState.value.size <= 1) {
                        FloatingActionButton(
                            modifier = Modifier.padding(20.dp),
                            onClick = { showNewWalletDialog = true },
                            shape = CircleShape,
                            containerColor = GreenStock

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_new_wallet),
                                contentDescription = "New Wallet",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Black
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            viewModel.addCryptoToWallet(
                                FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/"),
                                walletState.value[selectedWallet],
                                coinId,
                                Utilities().convertScientificToDecimal(quantityState.value).toString(),
                            )
                            showBottomSheet = false
                            showSuccesAnimation=true

                        }) {
                            Text(text = "Confirm")
                        }
                    }

                }
            }
        }
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
            Text(text = coinId, fontSize = 30.sp, fontFamily = titleFont(), color = Color.White)
            Text(
                text = "Valore per azione ${Utilities().convertScientificToDecimal(coinPriceState.value.toString())}$currency",
                color = Color.White
            )
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                TextField(
                    modifier = Modifier.padding(5.dp),
                    value = quantityState.value,
                    label = { Text("Amount") },
                    onValueChange = { newValue ->
                        if (newValue.isNotEmpty() || quantityState.value.isNotEmpty()) {
                            viewModel._quantity.value = decimalFormatter.cleanup(newValue)
                            if (newValue.isNotEmpty()) {
                                try {
                                    viewModel._totalSpent.value =
                                        (decimalFormatter.cleanup(newValue)
                                            .toFloat() * price).toString()
                                } catch (e: Exception) {
                                    viewModel._totalSpent.value = viewModel.totalSpent.value
                                }
                            } else {
                                viewModel._totalSpent.value =
                                    ""
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    modifier = Modifier.padding(5.dp),
                    value = totalSpentState.value,
                    label = { Text("Total spent") },
                    onValueChange = { newValue ->
                        if (newValue.isNotEmpty() || totalSpentState.value.isNotEmpty()) {
                            try {
                                viewModel._totalSpent.value = decimalFormatter.cleanup(newValue)
                            } catch (e: Exception) {
                                viewModel._totalSpent.value = viewModel.totalSpent.value
                            }
                            if (newValue.isNotEmpty()) {
                                viewModel._quantity.value =
                                    (decimalFormatter.cleanup(newValue)
                                        .toFloat() / price).toString()
                            } else {
                                viewModel._quantity.value = ""
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

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
                    onclick = {
                        navController.popBackStack()
                    },
                    text = "cancel",
                    textSize = 15.sp
                )
                SignButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 10.dp, end = 40.dp),
                    onclick = {
                        if (quantityState.value.isEmpty() || totalSpentState.value.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Inserisci la quantità o la spesa prima di continuare",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.getWalletsList(
                                database = FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/"),
                                viewModel.returnWalletListCallback
                            )
                            showBottomSheet = true
                        }

                    },
                    text = "buy",
                    textSize = 15.sp,
                    colors = ButtonDefaults.buttonColors(containerColor = RedStock)
                )
            }
        }
    }
}

