package com.nexusfalcao.designsystem.widget.navigationDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.designsystem.R
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.TopBarWidget
import kotlinx.coroutines.launch

@Composable
fun CustomNavigationScaffold(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    userPhotoId: Int?,
    userName: String?,
    toHome: () -> Unit,
    toNewRecipe: () -> Unit,
    toRecipeCatalog: () -> Unit,
    toAvatar: () -> Unit,
    onSignOut: () -> Unit,
    windowSizeClass: WindowSizeClass,
    content: @Composable (PaddingValues) -> Unit,
) {
    val isWidthExpanded = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
    val isWidthMedium = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM
    val drawerContent: @Composable () -> Unit = {
        DrawerBody(
            drawerState = if(isWidthExpanded) null else drawerState,
            userPhotoId = userPhotoId,
            userName = userName,
            toHome = toHome,
            toNewRecipe = toNewRecipe,
            toRecipeCatalog = toRecipeCatalog,
            toAvatar = toAvatar,
            onSignOut = onSignOut,
        )
    }

    if (isWidthMedium || isWidthExpanded) {
        PermanentNavigationDrawer(
            drawerContent = drawerContent,
        ) {
            Scaffold(
                content = content
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = drawerContent,
        ) {
            Scaffold(
                topBar = {
                    TopBarWidget(
                        drawerState = drawerState,
                    )
                },
                content = content
            )
        }
    }
}

@Composable
private fun DrawerBody(
    drawerState: DrawerState?,
    userPhotoId: Int?,
    userName: String?,
    toHome: () -> Unit,
    toNewRecipe: () -> Unit,
    toRecipeCatalog: () -> Unit,
    toAvatar: () -> Unit,
    onSignOut: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(start = 20.dp, end = 25.dp, top = 40.dp, bottom = 50.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        DrawerHeader(
            userPhotoId = userPhotoId,
            userName = userName,
            toAvatar = toAvatar,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier =
                Modifier
                    .height(1.dp)
                    .width(180.dp)
                    .background(color = MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.height(30.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_house,
            titleResourceId = R.string.drawer_home,
            onClick = {
                coroutineScope.launch {
                    drawerState?.close()
                    toHome()
                }
            },
        )

        DrawerTile(
            iconResourceId = R.drawable.ic_add,
            titleResourceId = R.string.drawer_new_recipe,
            onClick = {
                coroutineScope.launch {
                    drawerState?.close()
                    toNewRecipe()
                }
            },
        )

        DrawerTile(
            iconResourceId = R.drawable.ic_article,
            titleResourceId = R.string.drawer_recepies_store,
            onClick = {
                coroutineScope.launch {
                    drawerState?.close()
                    toRecipeCatalog()
                }
            },
        )

        Spacer(modifier = Modifier.weight(1.0f))

        DrawerTile(
            iconResourceId = R.drawable.ic_logout,
            titleResourceId = R.string.drawer_logout,
            onClick = {
                coroutineScope.launch {
                    drawerState?.close()
                    onSignOut()
                }
            },
        )
    }
}

@UIModePreview
@FontSizeAcessibilityPreview
@Composable
private fun DrawerPreview() {
    val user = PreviewParameterData.user
    val drawerState = rememberDrawerState(DrawerValue.Open)

    ReceptIaTheme {
        DrawerBody(
            userName = user.name,
            userPhotoId = user.photoId,
            drawerState = drawerState,
            toHome = {},
            toNewRecipe = {},
            toRecipeCatalog = {},
            toAvatar = {},
            onSignOut = {},
        )
    }
}
