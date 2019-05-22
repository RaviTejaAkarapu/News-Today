package com.newstoday

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.newstoday.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        val animDrawable = AnimatedVectorDrawableCompat.create(this, R.drawable.logo_anim)
        binding.logoSvg.setImageDrawable(animDrawable)
        animDrawable!!.registerAnimationCallback(
            object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    startActivity(Intent(this@SplashActivity, ChannelActivity::class.java))
                }
            })
        animDrawable.start()

        Handler().postDelayed({
            runOnUiThread {
                binding.appName.text = "News Today"
            }
        }, 2000)
    }
}
