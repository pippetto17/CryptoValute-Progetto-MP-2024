package it.magi.stonks.utilities


import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import it.magi.stonks.composables.ConfirmEmailDialog
import it.magi.stonks.viewmodels.RegistrationViewModel

class Utilities {

    fun capitalizeFirstChar(str: String): String {
        return str.replaceFirstChar { it.uppercase() }
    }

    fun convertDotsToCommas(input: String): String {
        return input.replace('.', ',')
    }

    fun convertCommasToDots(input: String): String {
        return input.replace(',', '.')
    }

    fun removeSpacesAndConvertToLowerCase(input: String): String {
        // Remove extra spaces and replace them with a single comma
        val noExtraSpaces = input.replace("\\s+".toRegex(), ",")

        // Convert to lowercase
        return noExtraSpaces.lowercase()
    }
    fun testSignup(context: Context, application: Application){
        RegistrationViewModel(application).registerUser(context,"test@gmail.com","Abc123,","Abc123,","TestName","TestSurname","EUR")


    }

    fun testSharedPreferences(application: Application){

    }
    @Composable
    fun EmailSentDialog(
        navController: NavController,
        onDismiss: () -> Unit,
        onDismissButton: () -> Unit,
        onConfirmButton: () -> Unit
    ){
        ConfirmEmailDialog(
            onDismissRequest = onDismiss,
            onDismissButton = onDismissButton,
            onConfirmButton = onConfirmButton
        )
    }
}