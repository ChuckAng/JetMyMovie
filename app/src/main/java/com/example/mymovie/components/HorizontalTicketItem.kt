package com.example.mymovie.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mymovie.R
import com.example.mymovie.screens.TicketDashLine
import com.example.mymovie.screens.TicketRowItem
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@Composable
fun HorizontalTicketItem(onClickTicket: () -> Unit) {
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    Box(
        modifier = Modifier
            .height(height * 0.3f)
            .fillMaxWidth()
            .padding(16.dp)
            .clip(
                TicketShape(
                    circleRadius = 16.dp,
                    cornerSize = CornerSize(16.dp),
                    orientation = TicketShape.ORIENTATION_HORIZONTAL
                )
            )
            .background(Color.White)
            .clickable(onClick = onClickTicket)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://images.pexels.com/photos/1366919/pexels-photo-1366919.jpeg?auto=compress&cs=tinysrgb&w=800"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.3f),
                contentScale = ContentScale.Crop
            )
            val rowItemModifier = Modifier
                .padding(horizontal = 0.dp)
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Suzume", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                SmallTicketRowItem(title = stringResource(R.string.hall), value = "01")
                SmallTicketRowItem(title = stringResource(R.string.row), value = "C")
                SmallTicketRowItem(title = stringResource(R.string.seat), value = "09")
                TicketRowItem(
                    modifier = rowItemModifier,
                    icon = Icons.Outlined.CalendarToday,
                    title = "4 FEB"
                )
                TicketRowItem(
                    modifier = rowItemModifier,
                    icon = Icons.Outlined.Alarm,
                    title = "1:00 PM"
                )
                TicketRowItem(
                    modifier = rowItemModifier,
                    icon = Icons.Outlined.AttachMoney,
                    title = "24.00"
                )
            }
            TicketDashLine(isHorizontal = false)
            Row(modifier = Modifier.weight(1f)) {
                val url = "dfg-dg2-3432-4234"
                Barcode(
                    type = BarcodeType.QR_CODE,
                    value = url,
                    resolutionFactor = 10,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}


@Composable
fun SmallTicketRowItem(title: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(title, fontWeight = FontWeight.Light)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
    }
}