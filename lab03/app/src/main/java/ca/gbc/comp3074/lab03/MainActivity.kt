package ca.gbc.comp3074.lab03

import android.os.Bundle
import android.telephony.CellInfoCdma
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.lab03.ui.theme.Lab03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab03Theme (dynamicColor = false){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyDeck(message = SampleData.items,
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Message(val author:String, val body:String)
@Composable
fun MyCard(msg: Message, modifier: Modifier){
    Row (modifier=modifier){
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier=Modifier.width(8.dp))

        var isSelected by remember{ mutableStateOf(false) }
        val surfaceBk by animateColorAsState(
            if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surface
        )

        Surface (modifier= Modifier.fillMaxSize()){
            Column (modifier = Modifier.clickable{isSelected= !isSelected}){
                Text(msg.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier=Modifier.width(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 10.dp,
                    color = surfaceBk,
                    modifier = Modifier.animateContentSize().padding(2.dp)
                ) {
                    Text(msg.body,
                        modifier=Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun PreviewMyCard(){
    Lab03Theme(dynamicColor = true) {
        MyCard(msg = Message("Joe", "Hello Android"), modifier = Modifier.padding(10.dp))
    }

}

@Composable
fun MyDeck(message: List<Message>, modifier: Modifier){
    LazyColumn(modifier=modifier) {
        items(message){message ->
            MyCard(
                msg=message,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
@Preview
@Composable
fun PrevieMyDecl(){
    Lab03Theme(dynamicColor = false) {
        MyDeck(message = SampleData.items,
            modifier = Modifier.padding(5.dp))
    }
}
