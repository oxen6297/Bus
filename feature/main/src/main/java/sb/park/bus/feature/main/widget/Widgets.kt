package sb.park.bus.feature.main.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextCoin(
    title: String,
    value: String,
    color: Color,
    fontSize: TextUnit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(
            text = value,
            style = TextStyle(color = color, fontSize = fontSize)
        )
    }
}