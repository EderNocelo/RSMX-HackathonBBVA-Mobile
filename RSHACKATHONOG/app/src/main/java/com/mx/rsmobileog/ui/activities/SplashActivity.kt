package com.mx.rsmobileog.ui.activities

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mx.rsmobileog.R
import com.mx.rsmobileog.databinding.SplashActivityBinding
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {
    // binding main layout
    private lateinit var bindingView: SplashActivityBinding

    /** @override onCreate
     *  Inflates main view and hide STATUS BAR
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflate and load layout
        bindingView = SplashActivityBinding.inflate(layoutInflater)
        setContentView(bindingView.root)
        // hide title bar
        supportActionBar!!.hide()
        // appear animation
        appearAnimation()
    }

    /** appearAnimation
     *  Appears UI animation elements
     * */
    private fun appearAnimation(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_fade)
        clMainContent.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                startLottieAnimation()
            }
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {}
        })
    }

    /** startLottieAnimation
     *  Execute Lottie animation and opens main Activity
     * */
    private fun startLottieAnimation(){
        lavSplash.visibility = View.VISIBLE
        lavSplash.playAnimation()
        lavSplash.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                openMainActivity()
            }
        })
    }

    /** openMainActivity
     *  Opens MainActivity and finish this
     * */
    private fun openMainActivity() {
        OwnerActivity.start(this)
        finish()
    }
}