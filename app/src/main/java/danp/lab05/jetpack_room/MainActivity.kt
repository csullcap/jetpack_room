package danp.lab05.jetpack_room

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

import danp.lab05.jetpack_room.ui.theme.Jetpack_roomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_roomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application)
                        )
                        ScreenSetup(viewModel)
                    }
            }
        }
    }
}

@Composable
fun TitleRow(head1: String, head2: String, head3: String,head4: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = Color.White,
            modifier = Modifier
                .weight(0.05f))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.15f))
        Text(head3, color = Color.White,
            modifier = Modifier
                .weight(0.25f))
        Text(head4, color = Color.White,
            modifier = Modifier
                .weight(0.2f))
    }
}

@Composable
fun ProductRow(id: Int, name: String, departamento:String,encargado:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(id.toString(), modifier = Modifier.weight(0.05f))
        Text(name, modifier = Modifier.weight(0.15f))
        Text(departamento, modifier = Modifier.weight(0.25f))
        Text(encargado, modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(5.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
    )
}

    class MainViewModelFactory(val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }

    @Composable
    fun ScreenSetup(viewModel: MainViewModel) {
        val allcems by viewModel.allCEMS.observeAsState(listOf())
        val searchcem by viewModel.searchCEM.observeAsState()

        MainScreen(
            allProducts = allcems,
            searchResults = searchcem,
            viewModel = viewModel
        )
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun MainScreen(
        allProducts: List<CEM>,
        searchResults: CEM?,
        viewModel: MainViewModel

    ) {
        var CEM_Name by remember { mutableStateOf("") }
        var CEM_Departamento by remember { mutableStateOf("") }
        var CEM_Encargado by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current

        val onCEM_NameTextChange = { text : String ->
            CEM_Name = text
        }

        val onCEM_DepartamentoTextChange = { text : String ->
            CEM_Departamento = text
        }

        val onCEM_EncargadoTextChange = { text : String ->
            CEM_Encargado = text
        }

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            CustomTextField(
                title = "CEM nombre",
                textState = CEM_Name,
                onTextChange = onCEM_NameTextChange,
                keyboardType = KeyboardType.Text
            )

            CustomTextField(
                title = "CEM Departamento",
                textState = CEM_Departamento,
                onTextChange = onCEM_DepartamentoTextChange,
                keyboardType = KeyboardType.Text
            )

            CustomTextField(
                title = "CEM Encargado",
                textState = CEM_Encargado,
                onTextChange = onCEM_EncargadoTextChange,
                keyboardType = KeyboardType.Text
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(onClick = {
                    if (CEM_Name.isNotEmpty() && CEM_Departamento.isNotEmpty() && CEM_Encargado.isNotEmpty()) {
                        viewModel.insertCEM(
                            CEM(
                                CEM_Name,
                                CEM_Departamento,
                                CEM_Encargado
                            )
                        )
                        CEM_Name = ""
                        CEM_Encargado = ""
                        CEM_Departamento = ""
                        keyboardController?.hide()
                    }
                }) {
                    Text("Add")
                }
            }
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    ) {
                val list = allProducts

                item {
                    TitleRow(
                        head1 = "ID",
                        head2 = "NOMBRE",
                        head3 = "DEPARTAMENTO",
                        head4 = "ENCARGADO"
                    )
                }

                items(list) { item ->
                    ProductRow(id = item.CEM_id, name = item.CEM_name, departamento = item.CEM_departamento, encargado = item.CEM_encargado)
                    Button(onClick = {
                        viewModel.deleteCEM(item.CEM_id)
                    }) {
                        Text("eliminar")

                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text("editar")
                    }
                }
            }
        }
    }
}

