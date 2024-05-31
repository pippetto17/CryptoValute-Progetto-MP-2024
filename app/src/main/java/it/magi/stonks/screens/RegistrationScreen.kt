package it.magi.stonks.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.composables.CustomPasswordField
import it.magi.stonks.composables.DropDown
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.title_font_size
import it.magi.stonks.viewmodels.RegistrationViewModel


@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val screenState = viewModel.screen.collectAsState()
    when (screenState.value) {
        1 -> {
            RegistrationScreen1(navController, viewModel)
        }

        2 -> {
            RegistrationScreen2(navController, viewModel)
        }
    }


}

@Composable
fun RegistrationScreen1(navController: NavController, viewModel: RegistrationViewModel) {
    val nameState = viewModel.name.collectAsState()
    val surnameState = viewModel.surname.collectAsState()
    val formFilter = "^[a-zA-Z\\s]+$".toRegex()



    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.login_background_design2),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(120.dp)
            )
            Column {
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = titleFont(),
                    fontSize = title_font_size,
                    color = TitleColor
                )
            }
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_name_label)), color = Color.White
                )
            }, value = nameState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                if (it.isEmpty() || it.matches(formFilter)) {
                    viewModel._name.value = it
                }
            })
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_surname_label)), color = Color.White
                )
            }, value = surnameState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                if (it.isEmpty() || it.matches(formFilter)) {
                    viewModel._surname.value = it
                }
            })
            Spacer(modifier = Modifier.height(9.dp))
            DropDown()
            Spacer(modifier = Modifier.fillMaxHeight(0.85f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { viewModel._screen.value = 2 },
                ) {

                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen2(navController: NavController, viewModel: RegistrationViewModel) {
    val emailState = viewModel.email.collectAsState()

    val passwordState = viewModel.password.collectAsState()
    val confirmPasswordState = viewModel.confirmPassword.collectAsState()
    var passwordErrors by rememberSaveable {
        mutableStateOf(listOf<Int>())
    }
    var showEmailDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showEmailDialog) {
        viewModel.EmailSentDialog(
            navController = navController,
            onDismiss = { showEmailDialog = false })
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background_design2),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_email_label)), color = Color.White
                )
            }, value = emailState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                viewModel._email.value = it
            })
            CustomPasswordField(
                value = passwordState.value,
                isError = passwordErrors.isNotEmpty(),
                labelId = R.string.signup_password_label,
                onValueChange = {
                    viewModel._password.value = it
                    passwordErrors = viewModel.validatePassword(it)
                },
                passwordErrors = passwordErrors
            )
            CustomPasswordField(value = confirmPasswordState.value,
                labelId = R.string.signup_confirm_password_label,
                onValueChange = {
                    viewModel._confirmPassword.value = it
                })
            Button(onClick = {
                Log.d(
                    "Signup",
                    "registerUser value: ${
                        viewModel.registerUser(
                            viewModel.email.value,
                            viewModel.password.value,
                            viewModel.confirmPassword.value,
                            viewModel.name.value,
                            viewModel.surname.value,
                            viewModel.currentCurrency.value
                        )
                    }"
                )
                if (viewModel.registerUser(
                        viewModel.email.value,
                        viewModel.password.value,
                        viewModel.confirmPassword.value,
                        viewModel.name.value,
                        viewModel.surname.value,
                        viewModel.currentCurrency.value
                    ) == 0
                ) {
                    Log.d("Signup", "Apro il dialog")
                    showEmailDialog = true
                } else {
                    println("Errore nella creazione utente firebase")
                }
            }) {
                Text(text = stringResource(id = R.string.signup_button_label))

            }
            Spacer(modifier = Modifier.fillMaxHeight(0.85f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { viewModel._screen.value = 1 },
                ) {

                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}
