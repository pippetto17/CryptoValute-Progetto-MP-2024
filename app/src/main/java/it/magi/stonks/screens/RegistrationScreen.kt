package it.magi.stonks.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.objects.CustomPasswordField
import it.magi.stonks.ui.theme.fontSize
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.viewmodels.RegistrationViewModel


@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel) {
    val nameState = viewModel.name.collectAsState()
    val surnameState = viewModel.surname.collectAsState()
    val emailState = viewModel.email.collectAsState()

    val formFilter = "^[a-zA-Z]+$".toRegex()

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
            painter = painterResource(id = R.drawable.login_background_design),
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
            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontFamily = titleFont(),
                    fontSize = fontSize,
                    color = Color.White
                )
            }
            OutlinedTextField(label = {
                Text(
                    stringResource(id = (R.string.signup_name_label)), color = Color.White
                )
            }, value = nameState.value, colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ), onValueChange = {
                if (it.isEmpty()||it.matches(formFilter)) {
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
                if (it.isEmpty()||it.matches(formFilter)) {
                    viewModel._surname.value = it
                }
            })
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
                            viewModel.confirmPassword.value
                        )
                    }"
                )
                if (viewModel.registerUser(
                        viewModel.email.value,
                        viewModel.password.value,
                        viewModel.confirmPassword.value
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
