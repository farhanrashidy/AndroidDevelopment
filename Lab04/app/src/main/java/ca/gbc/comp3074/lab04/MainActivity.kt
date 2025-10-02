package ca.gbc.comp3074.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.room.util.TableInfo
import ca.gbc.comp3074.lab04.ui.theme.Lab04Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding->
                    Column(modifier=Modifier.padding(innerpadding)){
                        ItemForm()
                        ListItems()
                    }
                }

            }
        }
    }
}


@Composable
fun ItemForm(){

    var name by remember{mutableStateOf("")}
    var quantity by remember {mutableStateOf(0)}
    var db = ItemsDatabase.getDatabase(LocalContext.current)

    Column {
        TextField(
            value = name,
            label={Text("Item name")},
            onValueChange = {name=it},
            modifier =Modifier.fillMaxWidth()
        )
        TextField(
            value = quantity.toString(),
            label = {Text("Quantity")},
            onValueChange = {
                newVal ->
                val v = newVal.filter{it.isDigit()}
                if(v.isNotEmpty()){
                    quantity = v.toInt()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =Modifier.fillMaxWidth()
        )
        val scope = rememberCoroutineScope()
        Button(modifier =Modifier.fillMaxWidth(),
            onClick = {
               if(name.isNotEmpty()){
                   scope.launch{
                       db.itemDao().insert(Item(name=name,quantity=quantity))
                       name = " "
                       quantity = 0
                   }
               }
            }) {Text("Add Item")}

    }
}

@Composable
fun ItemRow(item:Item){
    val scope = rememberCoroutineScope ()
    val db = ItemsDatabase.getDatabase(LocalContext.current)
    Row(modifier = Modifier.fillMaxWidth().clickable {
        scope.launch{
            db.itemDao().delete(item)
        }
    }, horizontalArrangement = Arrangement.SpaceAround){
        Text(item.name)
        Spacer(modifier= Modifier.width(20.dp))
        Text(item.quantity.toString())
    }
}

class ItemViewModel(private val db: ItemsDatabase): ViewModel(){
    val allItems: StateFlow<List<Item>> = db.itemDao().getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

@Composable
fun ListItems(viewModel: ItemViewModel = ItemViewModel(ItemsDatabase.getDatabase(LocalContext.current))){
    val items by viewModel.allItems.collectAsStateWithLifecycle()
    LazyColumn {
        items(items){
            item ->
            ItemRow(item)
        }
    }
}


