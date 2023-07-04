package com.example.receptia.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.titleMediumSmall
import com.example.receptia.ui.theme.titleSemiBoldMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerWidget(
    drawerState: DrawerState,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerBody() },
        content = content,
    )
}

@Composable
private fun DrawerBody() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(start = 20.dp, end = 25.dp, top = 40.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        DrawerHeader()

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
        )

        Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_add,
            titleResourceId = R.string.drawer_new_recipe,
        )

        Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_article,
            titleResourceId = R.string.drawer_recepies_store,
        )

        Spacer(modifier = Modifier.height(26.dp))

        DrawerTile(
            iconResourceId = R.drawable.ic_user,
            titleResourceId = R.string.drawer_profile,
        )

        Spacer(modifier = Modifier.weight(1.0f))

        DrawerTile(
            iconResourceId = R.drawable.ic_logout,
            titleResourceId = R.string.drawer_logout,
        )
    }
}

@Composable
private fun DrawerHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: Add avatar logic
        Image(
            painter = painterResource(id = R.drawable.image_user),
            contentDescription = null,
            modifier = Modifier.height(85.dp),
        )

        Spacer(modifier = Modifier.width(15.dp))

        // TODO: Add name logic
        Text(
            text = "Bernardo Lagos",
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleSemiBoldMedium,
            modifier = Modifier.width(105.dp),
        )
    }
}

@Composable
private fun DrawerTile(
    @DrawableRes iconResourceId: Int,
    @StringRes titleResourceId: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: Add logic
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            modifier = Modifier.width(26.dp),
        )

        Spacer(modifier = Modifier.width(15.dp))

        // TODO: Add logic
        Text(
            text = stringResource(id = titleResourceId),
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMediumSmall,
        )
    }
}
