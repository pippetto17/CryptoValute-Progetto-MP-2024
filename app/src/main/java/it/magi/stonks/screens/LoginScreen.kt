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
import it.magi.stonks.composables.SignDivisor
import it.magi.stonks.ui.theme.DarkBgColor
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.ui.theme.SignInColor
import it.magi.stonks.ui.theme.SignUpButtonsColor
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.ui.theme.title_font_size
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel,onSignInClick:()->Unit) {
    val auth = Firebase.auth
    val context = LocalContext.current
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var showEmailDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (showEmailDialog) {
        Utilities().EmailSentDialog(
            navController = navController,
            onDismiss = { showEmailDialog = false },
            onConfirmButton = { showEmailDialog = false },
            onDismissButton = { showEmailDialog = false }
        )
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
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = titleFont(),
                fontSize = title_font_size,
                color = Color.White
            )
            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = FormContainerColor,
                ),
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    CustomEmailField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it },
                    )
                    SignDivisor()
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
                            color = SignInColor,
                            fontSize = 14.sp,
                            fontFamily = titleFont()
                        )
                    }
                }
            }
            SignButton(
                onclick = {
                    if (viewModel.userLogin(auth, email, password, navController, context) == 0) {
                        showEmailDialog = true
                    }
                },
                text = stringResource(id = R.string.login_button_label)
            )
            SignDivisor(isForm = false)
            GoogleLoginButton({onSignInClick()})
            Spacer(modifier = Modifier.height(40.dp))
            SignButton(
                onclick = { navController.navigate("registration") },
                text = "Sign up for free",
                colors = ButtonDefaults.buttonColors(
                    containerColor = SignUpButtonsColor,
                    contentColor = DarkBgColor
                ),
            )

        }
    }
}