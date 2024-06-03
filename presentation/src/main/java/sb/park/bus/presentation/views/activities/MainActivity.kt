package sb.park.bus.presentation.views.activities

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import sb.park.bus.presentation.R
import sb.park.bus.presentation.common.base.BaseActivity
import sb.park.bus.presentation.databinding.ActivityMainBinding
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.utils.CheckNetwork

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