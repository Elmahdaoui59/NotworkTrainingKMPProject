package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

const val URL = "https://v2.jokeapi.dev/joke/Any?type=single"

@Composable
@Preview
fun App() {
    MaterialTheme {
        var client: HttpClient? by remember { mutableStateOf(null) }
        //to make sure that we're not creating a new client on every recomposition
        LaunchedEffect(Unit) {
            client = createPlatformHttpClient()
        }
        val scope = rememberCoroutineScope()
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        if (client!!.get(URL).status.value == 200) {
                            println("Joke received!")
                        }
                    }
                }) {
                    Text("Generate a joke")
                }
            }
        }
    }
}