package complaints.com.aparmentapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        activity = this
        val zoom = findViewById<View>(R.id.logo) as ImageView
        val zoomOut = findViewById<View>(R.id.tv_welcome) as TextView
        val zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        zoom.startAnimation(zoomAnimation)
        val animZoomOut = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_out)
        zoomOut.startAnimation(animZoomOut)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        Handler(Looper.getMainLooper()).postDelayed({
            if (SaveAppData.getLoginData() != null) {
                val i = Intent(this@SplashScreenActivity, MainActivity2::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
        var activity: Activity? = null
    }
}