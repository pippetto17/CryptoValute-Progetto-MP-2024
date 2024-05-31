package it.magi.stonks.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import it.magi.stonks.R
import it.magi.stonks.composables.CustomEmailField
import it.magi.stonks.composables.CustomPasswordField
import it.magi.stonks.composables.GoogleLoginButton
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.title_font_size
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.HomeViewModel
import it.magi.stonks.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val application: Application = LocalContext.current.applicationContext as Application
    val apiKey = stringResource(id = R.string.api_key)
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
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
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(120.dp)
            )
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

            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FormContainerColor,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 15.dp
                )
            ) {
                Column {
                    CustomEmailField(
                        value = email,
                        labelId = R.string.login_email_label,
                        onValueChange = { email = it },
                    )
                    Box(modifier = Modifier.background(Color.Gray, RoundedCornerShape(50.dp)))
                    CustomPasswordField(
                        value = password,
                        labelId = R.string.login_password_label,
                        onValueChange = { password = it },
                    )
                    TextButton(
                        onClick = { viewModel.retrieveCredentials(auth, email, context) },
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_forgot_password_label),
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.userLogin(auth, email, password, navController, context)
                }
            ) {
                Text(text = stringResource(id = R.string.login_button_label))
            }
            GoogleLoginButton()

            TextButton(onClick = { navController.navigate("registration") }) {
                Text(text = stringResource(id = R.string.login_signup_label))

            }
            Button(
                colors = ButtonDefaults.buttonColors(Color.Yellow),
                onClick = { Utilities().testSignup(application) })
            {
                Text(text = "TEST!!!", color = Color.Black)
            }
            Button(
                colors = ButtonDefaults.buttonColors(Color.Yellow),
                onClick = {
                    HomeViewModel(application).filterCoins(
                        context,
                        apiKey,
                        "eur"
                    )
                })
            {
                Text(text = "API TEST!!!", color = Color.Black)
            }

        }
    }
}