package com.nexusfalcao.designsystem.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.logoIconResource
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
    val imageVector =
        when (drawerEnabled) {
            true -> Icons.Default.Menu
            false -> Icons.AutoMirrored.Filled.ArrowBack
        }

    CenterAlignedTopAppBar(
        title = {
            if (title.isNullOrEmpty()) {
                Image(
                    painter = logoIconResource(),
                    contentDescription = null,
                    modifier = Modifier.height(55.dp),
                )
            } else {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
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
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
    )
}

@UIModePreview
@Composable
private fun TopBarLogoPreview() {
    ReceptIaTheme {
        TopBarWidget()
    }
}

@UIModePreview
@Composable
private fun TopBarTituloPreview() {
    ReceptIaTheme {
        TopBarWidget(
            title = "Receitas",
            drawerEnabled = false,
        )
    }
}
