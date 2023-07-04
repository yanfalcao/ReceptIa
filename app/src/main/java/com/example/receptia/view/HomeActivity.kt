package com.example.receptia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.ui.theme.ReceptIaTheme
import com.example.receptia.ui.theme.titleBoldLarge
import com.example.receptia.ui.widget.NavigationDrawerWidget
import com.example.receptia.ui.widget.TopBarWidget

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceptIaTheme {
                Body()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun Body() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = { TopBarWidget(drawerState) },
        ) { padding ->
            Text(
                text = stringResource(id = R.string.title_login),
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleBoldLarge,
                modifier = Modifier.width(300.dp).padding(padding),
            )
        }
    }
}
