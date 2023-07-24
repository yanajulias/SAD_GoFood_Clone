package aej.android.enthusiast.gofood_clone.designsystem.components.molecules

import aej.android.enthusiast.gofood_clone.R
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.AsphaltTheme
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.LocalContentAlpha
import aej.android.enthusiast.gofood_clone.designsystem.components.atoms.AsphaltText
import aej.android.enthusiast.gofood_clone.designsystem.ui.theme.GoFood_CloneTheme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.ContentAlpha

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AsphaltSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    placeholder: String = "",
    enabled: Boolean = true,
    focused: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier.background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            // Back button
            IconButton(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        }

        SearchTextField(
            query = query,
            modifier = modifier,
            onQueryChange = onQueryChange,
            onSearchFocusChange = onSearchFocusChange,
            onClearQuery = onClearQuery,
            placeholder = placeholder,
            enabled = enabled
        )
    }
}

/**
 * This is a stateless TextField for searching with a Hint when query is empty,
 * and clear and loading [IconButton]s to clear query or show progress indicator when
 * a query is in progress.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchTextField(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    placeholder: String,
    enabled: Boolean
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier.height(48.dp),
        color = AsphaltTheme.colors.cool_gray_1cCp_100,
        shape = RoundedCornerShape(12.dp),
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
            ) {

                if (query.isEmpty()) {
                    SearchHint(text = placeholder, modifier.padding(start = 48.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .onFocusChanged { onSearchFocusChange(it.isFocused) }
                            .focusRequester(focusRequester)
                            .padding(top = 15.dp, bottom = 12.dp, start = 0.dp, end = 8.dp),
                        enabled = enabled,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        )
                    )

                    if (query.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SearchHint(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        AsphaltText(
            text = text,
            color = Color(0xffBDBDBD)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DefaultPreview() {
    GoFood_CloneTheme() {
        AsphaltSearchBar(
            query = "Search",
            onQueryChange = {},
            onSearchFocusChange = {},
            onClearQuery = {},
            onBack = {},
            placeholder = "Search",
            enabled = false,
            focused = true,
        )
    }
}