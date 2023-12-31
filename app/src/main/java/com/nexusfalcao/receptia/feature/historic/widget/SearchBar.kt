package com.nexusfalcao.receptia.feature.historic.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.theme.BlackLightTransparent
import com.nexusfalcao.receptia.ui.theme.LightGray

@Composable
fun SearchBar(
    modifier: Modifier,
    updateSearchFilter: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)

    BasicTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            updateSearchFilter(text.text)
        },
        singleLine = true,
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = BlackLightTransparent,
                ambientColor = BlackLightTransparent,
            )
            .background(
                color = LightGray,
                shape = roundedCornerShape,
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(start = 15.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                )

                innerTextField()
            }
        },
    )
}
