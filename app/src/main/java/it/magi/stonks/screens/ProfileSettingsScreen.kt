package it.magi.stonks.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import it.magi.stonks.ui.theme.RedStock
import it.magi.stonks.ui.theme.titleFont
import it.magi.stonks.utilities.Utilities
import it.magi.stonks.viewmodels.SettingsViewModel

@Composable
fun ProfileSettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    user?.let {
        val authEmail = it.email

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                    /* Edit Profile Picture */
                    }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Card(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = RedStock
                ),
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp, bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Account Information",
                        fontFamily = titleFont(),
                        fontSize = 22.sp,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                    TextField(
                        value = "Name: " + Utilities().getAccountName(),
                        onValueChange = {},
                        modifier = Modifier.padding(bottom = 10.dp),
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                    TextField(
                        value = "Surname: " + Utilities().getAccountSurname(),
                        onValueChange = {},
                        modifier = Modifier.padding(bottom = 10.dp),
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                    TextField(
                        value = "Email: $authEmail",
                        onValueChange = {},
                        modifier = Modifier.padding(bottom = 10.dp),
                        readOnly = true,
                        enabled = false,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }
            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedStock
                )
            ) {
                Text(text = "Edit Password")
            }
        }
    }
}