package com.mas.flowlibre

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var mediaReceiver : BroadcastReceiver

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            println("Notification permission granted")
        } else {
            println("Notification permission denied")
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(this)
        enableEdgeToEdge()

        homeViewModel.setContext(this)
        requestNotificationPermission()
        setupMediaReceiver()


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
    private fun setupMediaReceiver() {
        println("🎵 MainActivity: Iniciando setupMediaReceiver")
        mediaReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent : Intent?) {
                println("🎵 MediaReceiver: onReceive llamado!")
                println("🎵 MainActivity: Intent action = ${intent?.action}")
                println("🎵 MainActivity: Extras = ${intent?.extras?.keySet()}")

                intent?.getStringExtra("command")?.let { command ->
                    println("🎵 MainActivity: Comando recibido = '$command'")
                    println("🎵 MainActivity: isPlaying actual = ${homeViewModel.isPlaying.value}")
                    homeViewModel.processMediaCommand(command)
                    println("🎵 MainActivity: processMediaCommand() llamado")
                } ?: run {
                    println("🎵 MainActivity: Intent sin comando extra")
                }
                println("🎵 ===== MainActivity: Broadcast terminado =====")
            }
        }

        val filter = IntentFilter("com.mas.flowlibre.MEDIA_CONTROL")
        registerReceiver(mediaReceiver, filter, Context.RECEIVER_EXPORTED)
        println("🎵 MainActivity: BroadcastReceiver registrado con éxito")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mediaReceiver)
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