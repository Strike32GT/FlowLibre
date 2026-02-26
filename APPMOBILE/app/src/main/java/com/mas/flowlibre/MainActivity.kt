package com.mas.flowlibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mas.flowlibre.data.datasource.RetrofitClient
import com.mas.flowlibre.presentation.navigation.AppNavigation
import com.mas.flowlibre.presentation.viewModel.HomeViewModel
import com.mas.flowlibre.ui.theme.FlowLibreTheme

class MainActivity : ComponentActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(this)
        enableEdgeToEdge()
        setContent {
            FlowLibreTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(homeViewModel = homeViewModel)
                }
            }
            //val navController = rememberNavController()
            //AppNavigation(navController)
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
    //val navController = rememberNavController()
    //AppNavigation(navController)
}