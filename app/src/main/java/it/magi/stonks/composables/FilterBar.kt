package it.magi.stonks.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import it.magi.stonks.R
import it.magi.stonks.ui.theme.FormContainerColor
import it.magi.stonks.viewmodels.StonksViewModel

@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    viewModel: StonksViewModel,
    onValueChange: (String) -> Unit = { viewModel._filter.value = it }
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val filterState = viewModel.filter.collectAsState()

    TextField(
        modifier = modifier
            .height(50.dp),
        value = filterState.value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.filter_bar_default_string),
            )
        },
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White
        ),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            disabledLabelColor = Color.White,
            cursorColor = Color.White,
            focusedContainerColor = FormContainerColor,
            unfocusedContainerColor = FormContainerColor,
            disabledContainerColor = FormContainerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }),
    )
}