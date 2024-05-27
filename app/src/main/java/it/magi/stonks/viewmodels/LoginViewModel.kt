package it.magi.stonks.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import it.magi.stonks.R
import it.magi.stonks.objects.GoogleLogin
import it.magi.stonks.ui.theme.fontSize
import it.magi.stonks.ui.theme.titleFont

class LoginViewModel : ViewModel() {
    @Composable
    fun LoginScreen(navController: NavController) {
        var auth = Firebase.auth
        val context = LocalContext.current
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
                    Button(onClick = {
                        FirebaseAuth.getInstance().currentUser?.reload()
                        Log.d("Login", "current user email is ${auth.currentUser?.email} and is verified ${auth.currentUser?.isEmailVerified}")
                        if (email.isNotEmpty() && password.isNotEmpty() && auth.currentUser?.isEmailVerified == true) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        Log.d("Login", "Login successful")
                                        navController.navigate("home")
                                    } else {
                                        Log.d("Login", "signInWithEmail:failure", task.exception)
                                        Toast.makeText(
                                            context,
                                            "Authentication failed.",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                }
                        } else {
                             if(email.isEmpty() || password.isEmpty()){
                                 Toast.makeText(
                                     context,
                                     "Please fill in all fields.",
                                     Toast.LENGTH_SHORT,
                                 ).show()
                             }
                             else if(auth.currentUser?.isEmailVerified == false) {
                                 Toast.makeText(
                                     context,
                                     "Please verify your email.",
                                     Toast.LENGTH_SHORT,
                                 ).show()
                             }
                        }

                    }) {
                        Text(text = stringResource(id = R.string.login_button_label))

                    }
                    GoogleLogin()

                    TextButton(onClick = { navController.navigate("signup") }) {
                        Text(text = stringResource(id = R.string.login_signup_label))

                    }
                }
            }
        }
    }
}






