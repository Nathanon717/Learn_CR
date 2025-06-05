package com.example.learncr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learncr.ui.components.ResourceImage
import com.example.learncr.ui.theme.LearnCRTheme
import com.example.learncr.util.formatCardNameToImageFilename

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnCRTheme {
                var selectedItem by remember { mutableStateOf(0) }
                val items = listOf("Quiz", "Cards", "Settings")
                val icons = listOf(Icons.Filled.Home, Icons.Filled.List, Icons.Filled.Settings)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = { Icon(icons[index], contentDescription = item) },
                                    label = { Text(item) },
                                    selected = selectedItem == index,
                                    onClick = { selectedItem = index }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    // Content based on selected item
                    when (selectedItem) {
                        0 -> Greeting(name = "Quiz Page", modifier = Modifier.padding(innerPadding))
                        1 -> {
                            // Content for "Cards" tab
                            val cardNames = listOf(
                                "P.E.K.K.A", "Knight", "Archers", "Hog Rider",
                                ".Elite Barbarians.", "Mini P.E.K.K.A", "Golem", "Bandit",
                                "  Royal Ghost  ", "Electro Wizard" // Added more variety
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding) // Apply padding from Scaffold
                                    .verticalScroll(rememberScrollState())
                                    .padding(16.dp), // Additional padding for content inside the scroll
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Clash Royale Cards",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                cardNames.forEach { cardName ->
                                    val filename = formatCardNameToImageFilename(cardName)
                                    ResourceImage(
                                        filename = filename,
                                        contentDescription = cardName,
                                        modifier = Modifier.height(200.dp) // Increased height for better view
                                    )
                                    Spacer(modifier = Modifier.height(16.dp)) // Increased spacer
                                }
                            }
                        }
                        2 -> Greeting(name = "Settings Page", modifier = Modifier.padding(innerPadding))
                    }
                }
            }
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
    LearnCRTheme {
        // Previewing the default state (Quiz page)
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Quiz", "Cards", "Settings")
        val icons = listOf(Icons.Filled.Home, Icons.Filled.List, Icons.Filled.Settings)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(icons[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Greeting(name = "Quiz Page", modifier = Modifier.padding(innerPadding))
        }
    }
}