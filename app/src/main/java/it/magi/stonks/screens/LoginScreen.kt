package it.magi.stonks.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.objects.GoogleLogin
import it.magi.stonks.ui.theme.LoginBgColor
import it.magi.stonks.ui.theme.LoginFormBgColor
import it.magi.stonks.ui.theme.fontSize
import it.magi.stonks.ui.theme.titleFont

@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LoginBgColor)
            .wrapContentHeight(Alignment.CenterVertically),
    ) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = LoginFormBgColor)
        ) {

            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.login_email_label),
                        color = Color.White
                    )
                },
                value = email,
                onValueChange = { email = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(id = R.string.login_password_label),
                        color = Color.White
                    )
                },
                value = password,
                onValueChange = { password = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.login_button_label))

            }
            GoogleLogin()

            TextButton(onClick = { navController.navigate("signup") }) {
                Text(text = stringResource(id = R.string.login_signup_label))

            }
        }
    }

}



