package it.magi.stonks.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.magi.stonks.R

@Composable
fun ConfirmDeleteDialog(
    onDismissRequest: () -> Unit,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Delete Account") },
        text = { Text(text = stringResource(id = R.string.confirm_delete_account_text)) },
        dismissButton = {
            Button(onClick = onDismissButton) {
                Text(
                    text = stringResource(id = R.string.confirm_delete_account_button_cancel_label),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirmButton) {
                Text(
                    text = stringResource(id = R.string.confirm_delete_account_button_label),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}