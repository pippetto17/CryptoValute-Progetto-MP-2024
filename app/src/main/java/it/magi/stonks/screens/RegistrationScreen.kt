package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.title_font_size
import it.magi.stonks.viewmodels.RegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown() {
    val list = listOf("EUR", "USD", "GBP", "JPY")

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf(list[0]) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}
        ) {
            TextField(
                modifier = Modifier
                    .border(0.5.dp, Color.White, RoundedCornerShape(4.dp))
                    .menuAnchor(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                list.forEachIndexed{ index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = list[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Currently selected: $selectedText")
    }
}

@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val nameState = viewModel.name.collectAsState()
    val surnameState = viewModel.surname.collectAsState()
    val currentCurrencyState = viewModel.currentCurrency.collectAsState()

    val formFilter = "^[a-zA-Z\\s]+$".toRegex()
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
            Spacer(modifier = Modifier.height(10.dp))
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
        }
    }
}
