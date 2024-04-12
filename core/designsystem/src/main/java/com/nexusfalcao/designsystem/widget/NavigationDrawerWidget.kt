package com.nexusfalcao.designsystem.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.R
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.titleMediumSmall
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerWidget(
    drawerState: DrawerState,
    userPhotoId: Int?,
    userName: String?,
    toHome: () -> Unit,
    toNewRecipe: () -> Unit,
    toRecipeCatalog: () -> Unit,
    toAvatar: () -> Unit,
    onSignOut: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerBody(
                drawerState = drawerState,
                userPhotoId = userPhotoId,
                userName = userName,
                toHome = toHome,
                toNewRecipe = toNewRecipe,
                toRecipeCatalog = toRecipeCatalog,
                toAvatar = toAvatar,
                onSignOut = onSignOut,
            )
        },
        content = content,
    )
}

@Composable
private fun DrawerBody(
    drawerState: DrawerState,
    userPhotoId: Int?,
    userName: String?,
    toHome: () -> Unit,
    toNewRecipe: () -> Unit,
    toRecipeCatalog: () -> Unit,
    toAvatar: () -> Unit,
    onSignOut: () -> Unit,
) {
    // TODO: Add signout logic
    /*val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val onSighOut: () -> Unit = {
        lifecycleScope.launch {
            val authUiClient = ReceptIaApplication.instance.googleAuthUiClient
            authUiClient.signOut()

            navController.navigateToLogin(popUp = true)
        }
    }*/

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(start = 20.dp, end = 25.dp, top = 40.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        DrawerHeader(
            userPhotoId = userPhotoId,
            userName = userName,
            toAvatar = toAvatar
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .height(1.dp)
                .width(200.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.height(30.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_house,
            titleResourceId = R.string.drawer_home,
            onClick = toHome,
            drawerState = drawerState,
        )

        DrawerTile(
            iconResourceId = R.drawable.ic_add,
            titleResourceId = R.string.drawer_new_recipe,
            onClick = toNewRecipe,
            drawerState = drawerState,
        )

        DrawerTile(
            iconResourceId = R.drawable.ic_article,
            titleResourceId = R.string.drawer_recepies_store,
            onClick = toRecipeCatalog,
            drawerState = drawerState,
        )

        /*Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_user,
            titleResourceId = R.string.drawer_profile,
        )*/

        Spacer(modifier = Modifier.weight(1.0f))

        DrawerTile(
            iconResourceId = R.drawable.ic_logout,
            titleResourceId = R.string.drawer_logout,
            drawerState = drawerState,
            onClick = onSignOut,
        )
    }
}

@Composable
private fun DrawerHeader(
    userPhotoId: Int?,
    userName: String?,
    toAvatar: () -> Unit,
) {
    val avatarRes = userPhotoId ?: R.drawable.img_user

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = null,
            modifier = Modifier
                .height(85.dp)
                .clickable { toAvatar() },
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = userName ?: "",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(105.dp),
        )
    }
}

@Composable
private fun DrawerTile(
    @DrawableRes iconResourceId: Int,
    @StringRes titleResourceId: Int,
    onClick: () -> Unit = {},
    drawerState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(50.dp)
            .width(200.dp)
            .clickable(onClick = {
                coroutineScope.launch {
                    drawerState.close()
                    onClick()
                }
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            modifier = Modifier.width(26.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = stringResource(id = titleResourceId),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMediumSmall,
        )
    }
}

@ThemePreview
@Composable
private fun NavigationDrawerPreview() {
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
