package it.magi.stonks.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import it.magi.stonks.R
import it.magi.stonks.ui.theme.GreenStock
import it.magi.stonks.ui.theme.RedStock

@Composable
fun ConfirmDeleteDialog(
    onDismissRequest: () -> Unit,
    onDismissButton: () -> Unit,
    onConfirmButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.confirm_delete_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.confirm_delete_account_text)) },
        dismissButton = {
            Button(
                onClick = onDismissButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenStock
                )
            ) {
                Text(
                    text = stringResource(id = R.string.confirm_delete_account_button_cancel_label),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedStock
                )
            ) {
                Text(
                    text = stringResource(id = R.string.confirm_delete_account_button_label),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}