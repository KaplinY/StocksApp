package com.littlelemon.interviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: StocksViewModel by viewModels()

        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: StocksViewModel) {

    val stocks by viewModel.filteredStocksState.collectAsState()
    val filterType by viewModel.filterType.collectAsState()

    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 30.dp)
        ) {
            FilterButton(
                label = "Stocks",
                isSelected = filterType == "stock",
                onClick = { viewModel.setFilterType("stock") }
            )
            FilterButton(
                label = "ETFs",
                isSelected = filterType == "etf",
                onClick = { viewModel.setFilterType("etf") }
            )
            FilterButton(
                label = "Crypto",
                isSelected = filterType == "crypto",
                onClick = { viewModel.setFilterType("crypto") }
            )
            FilterButton(
                label = "All",
                isSelected = filterType == null,
                onClick = { viewModel.setFilterType(null) }
            )
        }

        Column(
            modifier = Modifier.padding(start = 20.dp, top = 40.dp)
        ) {
            stocks.forEach { stock ->
                Column {
                    Text(
                        text = stock.name,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(text = stock.instrument_type, fontSize = 12.sp)
                    Text(text = stock.description, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Composable
fun FilterButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = label, color = if (isSelected) Color.Blue else Color.Black)
    }
}