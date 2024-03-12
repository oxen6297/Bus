package sb.park.bus.feature.main.views.activities

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.feature.main.R
import sb.park.bus.feature.main.common.base.BaseActivity
import sb.park.bus.feature.main.databinding.ActivityMainBinding
import sb.park.bus.feature.main.extensions.showToast
import sb.park.bus.feature.main.utils.CheckNetwork

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CheckNetwork(this).observe(this) {
            if (!it) {
                showToast(getString(R.string.toast_network))
            }
        }
    }
}