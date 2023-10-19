package com.example.receptia.view.widget

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.ReceptIaApplication
import com.example.receptia.feature.avatar.navigation.navigateToAvatar
import com.example.receptia.feature.historic.navigation.navigateToHistoric
import com.example.receptia.feature.home.navigation.navigateToHome
import com.example.receptia.feature.login.navigation.navigateToLogin
import com.example.receptia.feature.newRecipe.navigation.navigateToNewRecipe
import com.example.receptia.persistence.User
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.titleMediumSmall
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerWidget(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerBody(navController, drawerState) },
        content = content,
    )
}

@Composable
private fun DrawerBody(
    navController: NavController,
    drawerState: DrawerState
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val onSighOut: () -> Unit = {
        lifecycleScope.launch {
            val authUiClient = ReceptIaApplication.instance.googleAuthUiClient
            authUiClient.signOut()

            navController.navigateToLogin(popUp = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(start = 20.dp, end = 25.dp, top = 40.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        DrawerHeader(
            navController = navController,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .height(1.dp)
                .width(200.dp)
                .fillMaxWidth()
                .background(color = Green),
        )

        Spacer(modifier = Modifier.height(30.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_house,
            titleResourceId = R.string.drawer_home,
            onClick = navController::navigateToHome,
            drawerState = drawerState,
        )

        Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_add,
            titleResourceId = R.string.drawer_new_recipe,
            onClick = navController::navigateToNewRecipe,
            drawerState = drawerState,
        )

        Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_article,
            titleResourceId = R.string.drawer_recepies_store,
            onClick = navController::navigateToHistoric,
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
            onClick = onSighOut,
        )
    }
}

@Composable
private fun DrawerHeader(
    navController: NavController,
) {
    val user = User.find()

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: Add avatar logic
        Image(
            painter = painterResource(id = R.drawable.img_user),
            contentDescription = null,
            modifier = Modifier
                .height(85.dp)
                .clickable { navController.navigateToAvatar() },
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = user.name ?: "",
            color = Color.Black,
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
        modifier = Modifier.clickable(onClick = {
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
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = stringResource(id = titleResourceId),
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMediumSmall,
        )
    }
}
