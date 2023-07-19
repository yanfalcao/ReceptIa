package com.example.receptia.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWidget(
    drawerState: DrawerState? = null,
    title: String? = null,
    drawerEnabled: Boolean = true,
    onBackClick: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val imageVector = when (drawerEnabled) {
        true -> Icons.Default.Menu
        false -> Icons.Default.ArrowBack
    }

    CenterAlignedTopAppBar(
        title = {
            if (title.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.logo_icon_description),
                    modifier = Modifier.height(55.dp),
                )
            } else {
                Text(
                    text = title,
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    when (drawerEnabled) {
                        true -> drawerState?.open()
                        false -> onBackClick()
                    }
                }
            }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                )
            }
        },
    )
}
