package it.magi.stonks.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import it.magi.stonks.R
import it.magi.stonks.viewmodels.WalletViewModel

@Composable
fun NewWalletDialog(
    navController: NavController,
    onDismissRequest: () -> Unit,
    viewModel: WalletViewModel
) {
    var newWalletNameState = viewModel.newWalletName.collectAsState()
    val databaseUrl = stringResource(id = R.string.db_url)

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = newWalletNameState.value,
                    onValueChange = {
                        viewModel._newWalletName.value = it
                    },
                    placeholder = { Text(stringResource(R.string.dialog_new_wallet_name_placeholder)) },
                    label = { Text(stringResource(R.string.dialog_new_wallet_name_label)) })

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    try {
                        viewModel.createNewWallet(
                            newWalletNameState.value,
                            FirebaseDatabase.getInstance(databaseUrl), FirebaseAuth.getInstance()
                        )
                        navController.navigate("wallet") {
                            popUpTo("wallet") { inclusive = true }
                        }
                    } catch (e: Exception) {
                    }
                    onDismissRequest()
                }) {
                    Text(stringResource(R.string.dialog_new_wallet_name_create))
                    viewModel.getWalletsList(
                        database = FirebaseDatabase.getInstance("https://criptovalute-b1e06-default-rtdb.europe-west1.firebasedatabase.app/"),
                        viewModel.returnWalletListCallback


                    )

                }
            }
        }
    }
}