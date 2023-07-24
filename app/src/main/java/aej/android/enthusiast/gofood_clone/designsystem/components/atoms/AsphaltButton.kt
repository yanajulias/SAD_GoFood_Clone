package aej.android.enthusiast.gofood_clone.designsystem.components.atoms

import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.AsphaltTheme
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.LocalContentColor
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.contentColorFor
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.pressedColor
import aej.android.enthusiast.gofood_clone.designsystem.components.atoms.basic.Surface
import aej.android.enthusiast.gofood_clone.designsystem.components.atoms.utility.ButtonDefaults
import aej.android.enthusiast.gofood_clone.designsystem.ui.theme.GoFood_CloneTheme
import aej.android.enthusiast.gofood_clone.designsystem.utils.bounceClick
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed interface ButtonType {
    object Primary : ButtonType
    object Secondary : ButtonType
    object Outline : ButtonType
    object Text : ButtonType
}

@Composable
fun AsphaltButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = AsphaltTheme.shapes.medium,
    type: ButtonType = ButtonType.Primary,
    color: Color = AsphaltTheme.colors.gojek_green_500,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = when (type) {
        ButtonType.Primary -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) pressedColor(color) else color,
                contentColor = AsphaltTheme.colors.pure_white_500,
                disabledContainerColor = AsphaltTheme.colors.cool_gray_1cCp_500,
                disabledContentColor = AsphaltTheme.colors.pure_white_500
            )
        }

        ButtonType.Secondary -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) pressedColor(color) else color,
                contentColor = contentColorFor(color),
                disabledContainerColor = AsphaltTheme.colors.cool_gray_1cCp_100,
                disabledContentColor = AsphaltTheme.colors.cool_gray_500
            )
        }

        ButtonType.Outline -> {
            ButtonDefaults.outlinedButtonColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                contentColor = if (isPressed) pressedColor(color) else color,
                disabledContainerColor = AsphaltTheme.colors.pure_white_500,
                disabledContentColor = AsphaltTheme.colors.cool_gray_500
            )
        }

        ButtonType.Text -> {
            ButtonDefaults.textButtonColors(
                contentColor = color,
                disabledContentColor = color
            )
        }
    }

    val containerColor = colors.containerColor(enabled).value
    val contentColor = colors.contentColor(enabled).value

    val border: BorderStroke? = when (type) {
        ButtonType.Outline -> BorderStroke(1.dp, contentColor)
        else -> null
    }

    Surface(
        onClick = onClick,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .semantics { role = Role.Button }
            .bounceClick(),
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        interactionSource = interactionSource,
    ) {
        val textStyle = AsphaltTheme.typography.titleTinyBold

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides textStyle,
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingIcon?.let {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = contentColor,
                        strokeWidth = 2.dp
                    )
                } else {
                    content()
                }
                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    trailingIcon()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DefaultPreview() {
    GoFood_CloneTheme() {
        AsphaltButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            isLoading = false,
            onClick = {}
        ) {
            AsphaltText(text = "Test")
        }
    }
}