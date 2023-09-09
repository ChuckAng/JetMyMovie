package com.example.mymovie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovie.R

@Composable
fun SwitchTabBar(currentSelected: Boolean, onTabClicked: (Boolean) -> Unit) {
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    Row(
        modifier = Modifier
            .height(height * 0.15f)
            .fillMaxWidth()
            .padding(32.dp)
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
                .clip(RoundedCornerShape(CornerSize(16.dp)))
                .background(if (currentSelected) MaterialTheme.colorScheme.onTertiaryContainer else Color.Transparent)
                .clickable { onTabClicked(!currentSelected)},
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.today),
                color = MaterialTheme.colorScheme.background
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                .clip(RoundedCornerShape(CornerSize(12.dp)))
                .background(if (!currentSelected) MaterialTheme.colorScheme.onTertiaryContainer else Color.Transparent)
                .clickable {  onTabClicked(!currentSelected) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.upcoming),
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}