package com.example.mymovie.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mymovie.R
import com.example.mymovie.components.TicketShape
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType

@Composable
fun TicketDetailScreen() {
    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .height(height * 0.9f)
                .fillMaxWidth()
                .padding(32.dp)
                .clip(TicketShape(24.dp, CornerSize(16.dp)))
                .background(Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://images.pexels.com/photos/1366919/pexels-photo-1366919.jpeg?auto=compress&cs=tinysrgb&w=800"),
                    contentDescription = null,
                    modifier = Modifier
                        .height(height * 0.3f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Suzume",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                val rowItemModifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Start)
                TicketRowItem(
                    modifier = rowItemModifier,
                    icon = Icons.Outlined.PinDrop,
                    title = "AMC CLASSIC Deer Valley 16"
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TicketColumnItem(title = stringResource(R.string.hall), value = "08")
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.outline,
                    )
                    TicketColumnItem(title = stringResource(R.string.row), value = "C")
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    TicketColumnItem(title = stringResource(R.string.seat), value = "13")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TicketDashLine()
                TicketBarcode()
            }
        }
    }
}

@Composable
fun TicketRowItem(modifier: Modifier, icon: ImageVector, title: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
        Text(text = title, fontSize = 13.sp)
    }
}

@Composable
fun TicketColumnItem(title: String, value: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.ExtraLight)
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun TicketDashLine(
    isHorizontal: Boolean = true, color: Color = MaterialTheme.colorScheme.outline
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier = if (isHorizontal) {
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(1.dp)
        } else {
            Modifier
                .padding(vertical = 4.dp)
                .fillMaxHeight()
                .width(2.dp)
        }
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = if (isHorizontal) Offset(size.width, 0f) else Offset(0f, size.height),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun TicketBarcode() {
    val url = "dfg-dg2-3432-4234"
    if (BarcodeType.PDF_417.isValueValid(url)) {
        Barcode(
            type = BarcodeType.PDF_417,
            value = url,
            resolutionFactor = 10,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}