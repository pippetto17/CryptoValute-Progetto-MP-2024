package it.magi.stonks.screens

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import it.magi.stonks.objects.CustomTextField
import it.magi.stonks.ui.theme.LoginBgColor

@Composable
fun SignUpScreen() {
    Column {
        var email by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
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
            CustomTextField(
                value = email,
                onclick = {
                    email = it

                },
                label = "Email"
            )
            OutlinedTextField(
                label = { Text(text = "Password") },
                value = password,
                onValueChange = { password = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            OutlinedTextField(
                label = { Text(text = "Conferma Password") },
                value = ConfirmPassword,
                onValueChange = { ConfirmPassword = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Button(onClick = { CreateNewUser(email, password, ConfirmPassword) }) {
                Text(text = "Registrati")

            }
        }
    }

}

fun CreateNewUser(email: String, password: String, confirmPass: String) {
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
    } else {
        println("Email non valida o password non corrispondenti")
    }
}

fun checkValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
    return emailRegex.matches(email)
}