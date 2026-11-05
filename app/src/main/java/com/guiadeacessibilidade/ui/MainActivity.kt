package com.guiadeacessibilidade.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.GuiaDeAcessibilidadeApplication
import com.guiadeacessibilidade.ui.components.AppNavigation
import com.guiadeacessibilidade.ui.factory.GuidelineViewModelFactory
import com.guiadeacessibilidade.ui.theme.GuiaDeAcessibilidadeTheme
import com.guiadeacessibilidade.ui.viewmodel.SuccessCriteriaViewModel


private const val TAG = "MainActivity"

var someName by mutableStateOf("lar")

class MainActivity : ComponentActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val container = (application as GuiaDeAcessibilidadeApplication).appContainer

        val getAllSuccessCriteriaUseCase = container.getAllSuccessCriteriaUseCase

        val factory = GuidelineViewModelFactory(getAllSuccessCriteriaUseCase)

        val viewModel: SuccessCriteriaViewModel by viewModels { factory }

        //db = Firebase.firestore

        enableEdgeToEdge()
        setContent {
            AppNavigation(container)
            //SuccessCriteriaScreen(viewModel)
                //MyCounterCompose()
              //GetGuidelines { loadGuidelines() }
//            GuiaDeAcessibilidadeTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }

    fun loadGuidelines(){
        db.collection("guidelines")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuiaDeAcessibilidadeTheme {
        Greeting("Android")
    }
}