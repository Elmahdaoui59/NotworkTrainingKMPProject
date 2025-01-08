package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

const val URL = "https://v2.jokeapi.dev/joke/Any?type=single"

@Composable
@Preview
fun App() {
    MaterialTheme {
        var client: HttpClient? by remember { mutableStateOf(null) }
        var joke by remember {
            mutableStateOf<Joke?>(null)
        }
        //to make sure that we're not creating a new client on every recomposition
        LaunchedEffect(Unit) {
            client = createPlatformHttpClient().config {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
            }
        }
        val scope = rememberCoroutineScope()
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Button(onClick = {
                    scope.launch(Dispatchers.IO) {
                        joke = client!!.get(URL).body()
                    }
                }) {
                    Text("Generate a joke")
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = joke?.joke ?: "",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp
                )
            }
        }
    }
}