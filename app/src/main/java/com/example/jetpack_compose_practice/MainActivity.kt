package com.example.jetpack_compose_practice

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_practice.ui.theme.Jetpack_Compose_PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var names = listOf<String>("Android", "IOS", "Mike", "Sally", "Alex")
        setContent {
            Jetpack_Compose_PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    MessageCard(msg = Message("Android", "Jetpack Compose"))
                    Conversation(messages = SampleData.conversationSample)
//                    Column() {
//                        Greeting("Android")
//                        Greeting(name = "IOS")
//                    }
//                    RepeatedGreeting(name = names)
                }
            }
        }
    }
}

// @Composable : 위젯 생성 가능
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun RepeatedGreeting(name: List<String>) {
    Column() {
        for (i in name) {
            Greeting(name = i)
        }
    }
}
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Contact profile picture",
            // Set image size to 40 dp
            // Clip image to be shaped as a circle
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)

        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(targetValue =
            if(isExpanded) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                tonalElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp),
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn() {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}



// Light Mode & Dark Mode
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

// @Preview :: 애뮬레이터를 사용하지 않고 미리 볼 수 있음
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Jetpack_Compose_PracticeTheme {
//        Greeting("Android")
        MessageCard(msg = Message("Alex", "Hi, How are you?"))
    }
}

@Preview
@Composable
fun PreviewConversation() {
    Jetpack_Compose_PracticeTheme() {
        Conversation(messages = SampleData.conversationSample)
    }
}