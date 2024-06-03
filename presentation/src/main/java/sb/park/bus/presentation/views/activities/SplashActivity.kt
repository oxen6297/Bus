package sb.park.bus.presentation.views.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sb.park.bus.presentation.R
import sb.park.bus.presentation.common.base.BaseActivity
import sb.park.bus.presentation.databinding.ActivitySplashBinding
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.viewmodels.SplashViewModel

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionList = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val isAsked = permissionList.any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }

        if (isAsked) {
            goMain()
        } else {
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions.any { !it.value }) {
                    showToast(getString(R.string.toast_permission))
                }
                goMain()
            }.launch(permissionList)
        }
    }

    private fun goMain() {
        lifecycleScope.launch {
            viewModel.insertBitCoin()
            delay(1000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}