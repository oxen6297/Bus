package sb.park.bus.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.data.model.BaseResponse
import sb.park.bus.feature.main.navigation.NavScreen
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
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = NavScreen.MAIN.name) {
                        composable(NavScreen.MAIN.name){
                            MainScreen(navController = navController)
                        }
                        composable(NavScreen.SEARCH.name){
                            SearchScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MainScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
        ) {
            SearchTextField(navController)
            Spacer(modifier = Modifier.height(25.dp))
            TextBitCoin()
        }
    }

    @Composable
    private fun SearchTextField(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable {
                    navController.navigate(NavScreen.SEARCH.name)
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = stringResource(id = R.string.text_hint),
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }

    @Composable
    private fun TextBitCoin(viewModel: BitCoinViewModel = hiltViewModel()) {
        val bitCoinFlow by viewModel.bitCoinFlow.collectAsState()
        when (bitCoinFlow) {
            is UiState.Success -> {
                val bitCoinModel = (bitCoinFlow as UiState.Success<BaseResponse>).data.data
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
                //TODO progressbar
            }
        }
    }
}

