package it.magi.stonks.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.magi.stonks.R
import it.magi.stonks.ui.theme.LoginBgColor

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
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        OutlinedTextField(
            label = { Text(text = "Email") },
            value = email,
            onValueChange = {email=it},
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White)
        )
        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password,
            onValueChange = {password=it},
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White)
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Login")
            
        }
        TextButton(onClick = {  }) {
            Text(text = "Non sei ancora registrato? Registrati ora")
            
        }
        Button(
            onClick = {
                      navController.navigate("signup")
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = "",
                modifier=Modifier.size(20.dp)
            )
            Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
        }
    }
}