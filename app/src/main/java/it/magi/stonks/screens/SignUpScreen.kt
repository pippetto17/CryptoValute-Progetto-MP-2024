package it.magi.stonks.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import it.magi.stonks.R
import it.magi.stonks.objects.CustomTextField
import it.magi.stonks.ui.theme.LoginBgColor
import java.util.regex.Pattern


@Composable
fun SignUpScreen(navController: NavController) {
    Column {
        var email by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }

        var errors by rememberSaveable {
            mutableStateOf(listOf<Int>())
        }

        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }

        var ConfirmPassword by rememberSaveable {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LoginBgColor)
                .wrapContentHeight()
                .wrapContentWidth()
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {Text(stringResource(id = (R.string.signup_email_label)), color = Color.White)},
                isError = false
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errors = validatePassword(password)
                },
                isError = errors.isNotEmpty(),
                label = {Text(stringResource(id = (R.string.signup_password_label)), color = Color.White) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    errorTextColor = Color.White
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.size(25.dp),
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible)
                                ImageVector.vectorResource(R.drawable.ic_visible)
                            else ImageVector.vectorResource(R.drawable.ic_not_visible),
                            contentDescription = ""
                        )
                    }
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                for (error in errors) {
                    when (error) {
                        1 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_1_length),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        2 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_2_special_character),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        3 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_3_number),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )

                        4 -> Text(
                            text = stringResource(id = R.string.signup_password_error_code_4_uppercase),
                            modifier = Modifier.padding(start = 10.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
            OutlinedTextField(
                label = { Text(stringResource(id = (R.string.signup_confirm_password_label)),color = Color.White) },
                value = ConfirmPassword,
                onValueChange = { ConfirmPassword = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Button(
                onClick = {
                    if (CreateNewUser(email, password, ConfirmPassword) == 0) {
                        navController.navigate("login")
                    } else {
                        println("Errore nella creazione utente firebase")
                    }
                })
            {
                Text(text = stringResource(id = R.string.signup_button_label))

            }
        }
    }

}

fun CreateNewUser(email: String, password: String, confirmPass: String): Int {
    val auth: FirebaseAuth = Firebase.auth
    if (checkValidEmail(email) && password == confirmPass) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                println("Registrazione effettuata")
                Firebase.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }
            }
        }
        return 0
    } else {
        println("Email non valida o password non corrispondenti")
        return 1
    }
}

fun checkValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
    return emailRegex.matches(email)
}

fun validatePassword(password: String): List<Int> {
    val errors = mutableListOf<Int>()

    // Check password length
    if (password.length < 6) {
        errors.add(1) // Error code for insufficient length
    }

    // Check for special character
    val specialCharacterPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]")
    if (!specialCharacterPattern.matcher(password).find()) {
        errors.add(2) // Error code for missing special character
    }

    // Check for number
    val numberPattern = Pattern.compile("\\d")
    if (!numberPattern.matcher(password).find()) {
        errors.add(3) // Error code for missing number
    }

    // Check for uppercase letter
    if (!password.any { it.isUpperCase() }) {
        errors.add(4) // Error code for missing uppercase letter
    }


    return errors
}