package com.nexusfalcao.description.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val buttonShape = RoundedCornerShape(size = 15.dp)
    val buttonModifier =
        createButtonModifier(
            modifier = modifier,
            shape = buttonShape,
        )

    IconButton(
        onClick = onBackClick,
        modifier = buttonModifier.requiredSize(45.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )
    }
}

@Composable
private fun createButtonModifier(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
): Modifier {
    val backgroundColor = MaterialTheme.colorScheme.background
    val borderColor = MaterialTheme.colorScheme.onBackground

    return if (isSystemInDarkTheme()) {
        modifier
            .border(width = 2.dp, color = borderColor, shape = shape)
            .background(color = Color.Transparent, shape = shape)
    } else {
        modifier.background(color = backgroundColor, shape = shape)
    }
}

@UIModePreview
@Composable
fun BackButtonPreview() {
    ReceptIaTheme {
        BackButton()
    }
}
