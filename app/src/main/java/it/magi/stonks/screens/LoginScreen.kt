package it.magi.stonks.screens

import android.app.Application
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
import it.magi.stonks.composables.SignButton
import it.magi.stonks.composables.LoginDivisor
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.TitleColor
import it.magi.stonks.ui.theme.googleLoginLabelFont
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.title_font_size
import it.magi.stonks.viewmodels.LoginViewModel
import it.magi.stonks.viewmodels.RegistrationViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val auth = Firebase.auth
    val context = LocalContext.current
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
                fontFamily = googleLoginLabelFont() ,
                fontSize = title_font_size,
                color = Color.White
            )
            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FormContainerColor,
                ),
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomEmailField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        labelId = R.string.login_email_label,
                        onValueChange = { email = it },
                    )
                    LoginDivisor(
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(Color(0xFF364261))
                    )
                    CustomPasswordField(
                        modifier = Modifier.fillMaxWidth(),
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
                            color = TitleColor,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
            SignButton(
                onclick = {
                    viewModel.userLogin(auth, email, password, navController, context)
                },
                text = stringResource(id = R.string.login_button_label)
            )
            LoginDivisor(isForm = false)
            GoogleLoginButton()
            Spacer(modifier = Modifier.height(50.dp))
            SignButton(
                onclick = { navController.navigate("registration") },
                text = "Sign up for free",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2458E),
                    contentColor = DarkBgColor
                ),
            )

        }
    }
}