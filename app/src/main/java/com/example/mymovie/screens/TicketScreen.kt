package com.example.mymovie.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.mymovie.components.HorizontalTicketItem
import com.example.mymovie.components.SwitchTabBar

@Composable
fun TicketScreen() {
    var isSelected by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SwitchTabBar(currentSelected = isSelected, onTabClicked = { isSelected = it })
        LazyColumn() {
            if (isSelected) {
                items(5) {
                    HorizontalTicketItem({
                    })
                }
            } else {
                items(3) {
                    HorizontalTicketItem({
                    })
                }
            }
        }
    }
}
