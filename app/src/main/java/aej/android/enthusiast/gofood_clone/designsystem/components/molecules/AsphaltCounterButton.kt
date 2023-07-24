package aej.android.enthusiast.gofood_clone.designsystem.components.molecules

import aej.android.enthusiast.gofood_clone.R
import aej.android.enthusiast.gofood_clone.designsystem.ui.asphalt.AsphaltTheme
import aej.android.enthusiast.gofood_clone.designsystem.components.atoms.AsphaltText
import aej.android.enthusiast.gofood_clone.designsystem.ui.theme.GoFood_CloneTheme
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AsphaltCounterButton(
    modifier: Modifier = Modifier,
    value: Int,
    onDecrementClick: (Int) -> Unit,
    onIncrementClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            modifier = Modifier
                .size(28.dp),
            enabled = value > 0,
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(AsphaltTheme.colors.cool_gray_1cCp_100),
            onClick = { onDecrementClick(value) }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = null)
        }
        Spacer(modifier = Modifier.width(10.dp))
        AsphaltText(
            text = "$value",
            modifier = Modifier.widthIn(min = 24.dp),
            style = AsphaltTheme.typography.titleModerateDemi.copy(
                fontWeight = FontWeight.Normal
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        FilledIconButton(
            modifier = Modifier
                .size(28.dp),
            enabled = value <= 100,
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(AsphaltTheme.colors.cool_gray_1cCp_100),
            onClick = { onIncrementClick(value) }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun DefaultPreview() {
    GoFood_CloneTheme() {
        AsphaltCounterButton(value = 1, onDecrementClick = {}, onIncrementClick = {})
    }
}