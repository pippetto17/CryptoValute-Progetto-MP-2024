package it.magi.stonks.viewmodels

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import it.magi.stonks.R
import it.magi.stonks.ui.theme.LoginBgColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.regex.Pattern

class RegistrationViewModel : ViewModel() {

    var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    var _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    var _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword


    fun registerUser(email: String, password: String, confirmPassword: String): Int {
        Log.d("Signup", "registerUser email: $email")
        Log.d("Signup", "registerUser password: $password")
        Log.d("Signup", "registerUser confirmPassword: $confirmPassword")
        val auth: FirebaseAuth = Firebase.auth
        if (checkValidEmail(email) && password == confirmPassword) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Signup", "User created successfully")
                    Firebase.auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Signup", "Email sent.")
                            } else {
                                Log.d("Signup", "Error, email not sent.")
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
        Log.d("Signup", "Password length: ${password.length}")
        if (password.length < 6) {
            errors.add(1) // Error code for insufficient length
        }

        // Check for special character
        val specialCharacterPattern =
            Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]")
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

    @Composable
    fun EmailSentDialog(navController: NavController, onDismiss: () -> Unit) {
        Dialog(onDismissRequest = { navController.navigate("login") }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.email_sent_dialog_text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(id = R.string.email_sent_dialog_send_again_text),
                        textAlign = TextAlign.Center
                    )
                }
                Button(onClick = {
                    navController.navigate("login")
                }) {
                    Text(
                        text = stringResource(id = R.string.email_sent_dialog_button_label),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}