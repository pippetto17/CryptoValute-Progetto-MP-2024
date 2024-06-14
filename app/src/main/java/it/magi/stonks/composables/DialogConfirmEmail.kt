package it.magi.stonks.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.magi.stonks.R
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock

@Composable
fun ConfirmEmailDialog(
    onDismissRequest: () -> Unit,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Confirm Email") },
        text = { Text(text = stringResource(id = R.string.email_sent_dialog_text)) },
        dismissButton = {
            Button(
                onClick = onDismissButton,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedStock
                )
            ) {
                Text(
                    text = stringResource(id = R.string.email_sent_dialog_send_again_text),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmButton,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenStock
                )
            ) {
                Text(
                    text = stringResource(id = R.string.email_sent_dialog_button_label),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}