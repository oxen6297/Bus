package sb.park.bus.feature.main.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseActivity
import sb.park.bus.feature.main.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}