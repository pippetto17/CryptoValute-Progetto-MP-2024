package it.magi.stonks.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import it.magi.stonks.R
import it.magi.stonks.objects.CustomEmailField
import it.magi.stonks.objects.CustomPasswordField
import it.magi.stonks.objects.Utilities
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.TitleFontSize
import it.magi.stonks.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val apiKey= stringResource(id = R.string.api_key)
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background_design),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically),
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontFamily = titleFont(),
                fontSize = TitleFontSize,
                color = TitleColor
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CustomEmailField(
                    value = email,
                    labelId = R.string.login_email_label,
                    onValueChange = { email = it },
                )
                CustomPasswordField(
                    value = password,
                    labelId = R.string.login_password_label,
                    onValueChange = { password = it },
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        viewModel.userLogin(auth, email, password, navController, context)
                    }
                ) {
                    Text(text = stringResource(id = R.string.login_button_label))
                }

                viewModel.GoogleLogin()

                TextButton(onClick = { navController.navigate("registration") }) {
                    Text(text = stringResource(id = R.string.login_signup_label))

                }
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Yellow),
                    onClick = { Utilities().testSignup() })
                {
                    Text(text = "TEST!!!", color = Color.Black)
                }
            }
        }
    }
}