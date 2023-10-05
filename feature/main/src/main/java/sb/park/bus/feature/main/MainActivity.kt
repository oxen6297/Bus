package sb.park.bus.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.data.model.CoinBaseModel
import sb.park.bus.feature.main.theme.BusTheme
import sb.park.bus.feature.main.theme.UiState
import sb.park.bus.feature.main.viewmodels.BitCoinViewModel
import sb.park.bus.feature.main.widget.TextCoin

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    private fun MainScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SearchTextField()
                SearchIconButton(modifier = Modifier.align(Alignment.CenterEnd))
            }
            Spacer(modifier = Modifier.height(25.dp))
            TextBitCoin()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchTextField() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                ),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_hint),
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    clickSearchBtn()
                    keyboardController?.hide()
                }
            )
        )
    }

    @Composable
    private fun SearchIconButton(modifier: Modifier) {
        IconButton(
            onClick = { clickSearchBtn() },
            modifier = modifier
                .size(48.dp)
                .padding(end = 15.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

    private fun clickSearchBtn() {
        //TODO 검색 버튼 클릭 로직 작성
    }

    @Composable
    private fun TextBitCoin(viewModel: BitCoinViewModel = viewModel()) {
        val bitCoinFlow by viewModel.bitCoinFlow.collectAsState()
        when (bitCoinFlow) {
            is UiState.Success -> {
                val bitCoinModel = (bitCoinFlow as UiState.Success<CoinBaseModel>).data.data
                Column(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    TextCoin(
                        title = stringResource(id = R.string.bit_coin),
                        value = bitCoinModel.date,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                    TextCoin(
                        title = stringResource(id = R.string.min_price),
                        value = bitCoinModel.minPrice,
                        color = Color.Blue,
                        fontSize = 13.sp
                    )
                    TextCoin(
                        title = stringResource(id = R.string.max_price),
                        value = bitCoinModel.maxPrice,
                        color = Color.Red,
                        fontSize = 13.sp
                    )
                    TextCoin(
                        title = stringResource(id = R.string.fluctate_rate),
                        value = bitCoinModel.changeRatio,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }

            is UiState.Error -> {
                Toast.makeText(this, stringResource(id = R.string.toast), Toast.LENGTH_SHORT).show()
                (bitCoinFlow as UiState.Error).e.printStackTrace()
            }

            is UiState.Loading -> {

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        BusTheme {
            SearchTextField()
        }
    }
}

