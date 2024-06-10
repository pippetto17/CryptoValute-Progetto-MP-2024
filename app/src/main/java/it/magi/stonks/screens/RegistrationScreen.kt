package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CustomEmailField
import it.magi.stonks.composables.SignField
import it.magi.stonks.composables.CustomPasswordField
import it.magi.stonks.composables.DropDown
import it.magi.stonks.composables.SignButton
import it.magi.stonks.composables.SignDivisor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.RegistrationViewModel


@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val screenState = viewModel.screen.collectAsState()

    when (screenState.value) {
        1 -> {
            FirstRegistrationScreen(navController, viewModel)
        }

        2 -> {
            SecondRegistrationScreen(navController, viewModel)
        }
    }


}

@Composable
fun FirstRegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val nameState = viewModel.name.collectAsState()
    val surnameState = viewModel.surname.collectAsState()
    val formFilter = "^[a-zA-Z\\s]+$".toRegex()

    val context = LocalContext.current
    val apiKey = stringResource(R.string.api_key)
    viewModel.getSupportedCurrencies(apiKey)
    val currencyListState = viewModel.getCurrencyList().observeAsState()
    val currencyList = currencyListState.value ?: listOf("EUR", "USD")

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.login_background_design),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FormContainerColor,
                ),
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.app_logo),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.signup_label),
                        textAlign = TextAlign.Center,
                        fontFamily = titleFont(),
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 20.dp),
                        text = stringResource(R.string.signup_subtitle_1),
                        textAlign = TextAlign.Center,
                        fontFamily = titleFont(),
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                    SignField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nameState.value,
                        labelID = R.string.signup_name_label,
                        drawableID = R.drawable.ic_user_info,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(formFilter)) {
                                viewModel._name.value = it
                            }
                        }
                    )
                    SignDivisor()
                    SignField(
                        modifier = Modifier.fillMaxWidth(),
                        value = surnameState.value,
                        labelID = R.string.signup_surname_label,
                        drawableID = R.drawable.ic_user_info,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(formFilter)) {
                                viewModel._surname.value = it
                            }
                        }
                    )
                    SignDivisor()
                    DropDown(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel,
                        currencyList
                    )
                }
            }
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
                    onclick = { navController.popBackStack() },
                    text = stringResource(R.string.signup_back_to_login),
                    textSize = 15.sp
                )
                SignButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 10.dp, end = 40.dp),
                    onclick = { viewModel._screen.value = 2 },
                    text = stringResource(R.string.next_screen_button_label),
                    textSize = 15.sp,
                    colors = ButtonDefaults.buttonColors(containerColor = RedStock)
                )
            }
        }
    }
}

@Composable
fun SecondRegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val emailState = viewModel.email.collectAsState()
    val context = LocalContext.current
    val passwordState = viewModel.password.collectAsState()
    val confirmPasswordState = viewModel.confirmPassword.collectAsState()
    var passwordErrors by rememberSaveable {
        mutableStateOf(listOf<Int>())
    }
    var showEmailDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showEmailDialog) {
        Utilities().EmailSentDialog(
            navController = navController,
            onDismiss = {
                showEmailDialog = false
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
            onConfirmButton = {
                showEmailDialog = false
                navController.navigate("login") {
                    popUpTo(0)
                }
            },
            onDismissButton = {
                showEmailDialog = false
                navController.navigate("login") {
                    popUpTo(0)
                }
            }
        )
    }
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.login_background_design),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FormContainerColor,
                ),
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.app_logo),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.signup_label),
                        textAlign = TextAlign.Center,
                        fontFamily = titleFont(),
                        fontSize = 30.sp,
                        color = Color.White,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 20.dp),
                        text = stringResource(R.string.signup_subtitle_2),
                        textAlign = TextAlign.Center,
                        fontFamily = titleFont(),
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                    CustomEmailField(
                        modifier = Modifier.fillMaxWidth(),
                        value = emailState.value,
                        onValueChange = {
                            viewModel._email.value = it
                        }
                    )
                    SignDivisor()
                    CustomPasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        value = passwordState.value,
                        isError = passwordErrors.isNotEmpty(),
                        labelId = R.string.signup_password_label,
                        onValueChange = {
                            viewModel._password.value = it
                            passwordErrors = viewModel.validatePassword(it)
                        },
                        passwordErrors = passwordErrors
                    )
                    SignDivisor()
                    CustomPasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        value = confirmPasswordState.value,
                        labelId = R.string.signup_confirm_password_label,
                        onValueChange = {
                            viewModel._confirmPassword.value = it
                        }
                    )
                }
            }
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
                    onclick = { viewModel._screen.value = 1 },
                    text = stringResource(R.string.signup_previous),
                    textSize = 15.sp
                )
                SignButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(end = 40.dp, start = 10.dp),
                    onclick = {
                        if (viewModel.registerUser(
                                context,
                                viewModel.email.value,
                                viewModel.password.value,
                                viewModel.confirmPassword.value,
                                viewModel.name.value,
                                viewModel.surname.value,
                                viewModel.selectedCurrency.value
                            ) == 0
                        ) {
                            Log.d("Signup", "Apro il dialog")
                            showEmailDialog = true
                        } else {
                            println("Errore nella creazione utente firebase")
                        }
                    },
                    text = stringResource(R.string.signup_label),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RedStock
                    ),
                    textSize = 15.sp
                )
            }
        }
    }
}
