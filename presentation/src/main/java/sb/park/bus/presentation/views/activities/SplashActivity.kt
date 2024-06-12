package sb.park.bus.presentation.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sb.park.bus.presentation.R
import sb.park.bus.presentation.common.base.BaseActivity
import sb.park.bus.presentation.databinding.ActivitySplashBinding
import sb.park.bus.presentation.extensions.showToast
import sb.park.bus.presentation.viewmodels.SplashViewModel

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()
    private val permissionLauncher: ActivityResultLauncher<Array<String>> by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.any { !it.value }) {
                showToast(getString(R.string.toast_permission))
            }
            goMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionList = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val isAsked = permissionList.any {
            ActivityCompat.shouldShowRequestPermissionRationale(this, it)
        }

        when (isAsked) {
            true -> goMain()
            else -> permissionLauncher.launch(permissionList)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun goMain() {
        lifecycleScope.launch {
            viewModel.insertBitCoin()
            viewModel.fileFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest {
                binding.progressbar.progress = it
                binding.textProgress.text = "$it%"
                if (it == 100) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            }
        }
    }
}