package it.magi.stonks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class WalletScreenViewModel : ViewModel() {
    @Composable
    fun WalletScreen(navController: NavController) {
        var balance by rememberSaveable { mutableStateOf("1.00 USD") }
        var transactionState by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Saldo del Portafoglio: $balance", color = Color.Black, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /* Implementa la logica di invio qui */ }) {
                Text(text = "Invia Criptovaluta")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Implementa la logica di ricezione qui */ }) {
                Text(text = "Ricevi Criptovaluta")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Stato della Transazione: $transactionState", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}