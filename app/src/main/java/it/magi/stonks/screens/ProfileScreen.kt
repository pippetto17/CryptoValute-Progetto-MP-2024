package it.magi.stonks.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import it.magi.stonks.R
import it.magi.stonks.composables.OtherDropDown
import it.magi.stonks.composables.OtherTopAppBar
import it.magi.stonks.composables.SignButton
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProfileScreen(navController: NavController, viewModel: SettingsViewModel) {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val application = LocalContext.current.applicationContext as Application

    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    viewModel.getSupportedCurrencies(apiKey)
    val currencyListState = viewModel.getCurrencyList().observeAsState()
    val currencyList = currencyListState.value ?: listOf("EUR", "USD")

    val authEmail = user?.email

    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showDeleteDialog) {
        Utilities().DeleteAccountDialog(
            onDismiss = {
                showDeleteDialog = false
            },
            onConfirmButton = {
                showDeleteDialog = false
                viewModel.viewModelScope.launch { viewModel.deleteFirebaseUser(context)}
            },
            onDismissButton = {
                showDeleteDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OtherTopAppBar(
                navController = navController
            )
        },
        containerColor = FormContainerColor,
        contentWindowInsets = WindowInsets(left = 0.dp, top = 0.dp, right = 0.dp, bottom = 0.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.size(160.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, GreenStock),
            ) {
                Image(
                    painter = painterResource(R.drawable.totti),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.profile_screen_your_information),
                color = Color.White,
                fontFamily = titleFont(),
                fontSize = 26.sp,
                modifier = Modifier.align(CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp)
            ) {
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
                                value = stringResource(R.string.name),
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
                                value = stringResource(R.string.surname),
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
                                value = stringResource(R.string.email),
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
                                value = "$authEmail",
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
                        OtherDropDown(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            viewModel,
                            currencyList,
                            context
                        )
                    }
                }

                SignButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onclick = { viewModel.logOut(context) },
                    text = stringResource(R.string.profile_screen_button_logout),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBgColor,
                        contentColor = Color.White
                    ),
                    textSize = 15.sp
                )
                SignButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onclick = { showDeleteDialog = true },
                    text = stringResource(R.string.profile_screen_button_delete_account),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBgColor,
                        contentColor = Color.Red
                    ),
                    textSize = 15.sp,
                )
            }
        }
    }
}