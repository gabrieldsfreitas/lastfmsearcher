package br.com.gabrieldsfreitas.lastfmsearcher.util

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import br.com.gabrieldsfreitas.lastfmsearcher.R

object AnimationUtils {

    fun showViewWithAnim(
        view: View, @AnimRes anim: Int = R.anim.fade_in, duration: Int = 0,
        fillAfter: Boolean? = null, context: Context? = null
    ) {

        if (view.visibility == View.VISIBLE) return

        view.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context ?: view.context, anim)
        if (duration > 0) {
            animation.duration = duration.toLong()
        }
        if (fillAfter != null) {
            animation.fillAfter = fillAfter
        }
        view.startAnimation(animation)
    }

    fun hideViewWithAnim(
        view: View, @AnimRes anim: Int = R.anim.fade_out,
        duration: Int = 0, fillAfter: Boolean? = null, context: Context? = null
    ) {

        if (view.visibility == View.GONE) return

        view.visibility = View.GONE
        val animation = AnimationUtils.loadAnimation(context ?: view.context, anim)
        if (duration > 0) {
            animation.duration = duration.toLong()
        }
        if (fillAfter != null) {
            animation.fillAfter = fillAfter
        }
        view.startAnimation(animation)
    }

    fun replaceView(
        view1: View, view2: View, @AnimRes animOut: Int = R.anim.fade_out,
        @AnimRes animIn: Int = R.anim.fade_in
    ) {

        if (view1.visibility != View.VISIBLE && view2.visibility == View.VISIBLE) {
            return
        }
        hideViewWithAnim(view1, animOut)
        showViewWithAnim(view2, animIn)
    }
}