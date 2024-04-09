package sb.park.bus.feature.main.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseActivity
import sb.park.bus.feature.main.databinding.ActivitySplashBinding
import sb.park.bus.feature.main.viewmodels.SplashViewModel

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.insertBitCoin()
            delay(500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}