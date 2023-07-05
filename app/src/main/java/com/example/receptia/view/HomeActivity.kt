package com.example.receptia.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.LightGreen
import com.example.receptia.ui.theme.ReceptIaTheme
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
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 25.dp),
            ) {
                Banner()

                Text(
                    text = stringResource(id = R.string.last_recipes_title),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
private fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
            .background(
                color = LightGreen,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 20.dp,
                bottom = 10.dp,
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_snacks),
            contentDescription = null,
            modifier = Modifier
                .height(122.dp)
                .width(113.dp).align(Alignment.BottomEnd),
        )

        Column {
            Text(
                text = stringResource(id = R.string.banner_title),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(230.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // TODO: Make login logic
                },
                colors = ButtonDefaults.buttonColors(containerColor = Green),
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
