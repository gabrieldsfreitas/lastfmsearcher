package br.com.gabrieldsfreitas.lastfmsearcher


import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import androidx.annotation.AnimRes

object AnimationUtils {

    fun showViewWithAnimAndContext(view: View, context: Context) {
        showViewWithAnim(view, context = context)
    }

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

    fun hideViewWithAnimAndContext(view: View, context: Context) {
        hideViewWithAnim(view, context = context)
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

    fun moveViewY(duration: Int, fromYDelta: Float, toYDelta: Float, vararg views: View) {
        moveViewY(duration, fromYDelta, toYDelta, null, *views)
    }

    fun moveViewY(
        duration: Int,
        fromYDelta: Float,
        toYDelta: Float,
        applyTransformation: Runnable?,
        vararg views: View
    ) {
        val anim = object : TranslateAnimation(0f, 0f, fromYDelta, toYDelta) {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                super.applyTransformation(interpolatedTime, t)

                applyTransformation?.run()
            }
        }

        anim.fillAfter = true
        anim.duration = duration.toLong()

        for (view in views) {
            view.startAnimation(anim)
        }
    }

    fun expand(v: View) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }


    fun applyFlippingEffect(context: Context, view: ViewFlipper) {
        view.setInAnimation(context, R.anim.fade_out)
        view.setOutAnimation(context, R.anim.fade_out)
    }

    fun collapse(itemView: ViewGroup, headerHeight: Int, extraHeight: Int = 0) {
        itemView.measure(0, 0)
        doAnim(itemView, ValueAnimator.ofInt(itemView.measuredHeight + extraHeight, headerHeight))
    }


    fun expand(itemView: ViewGroup, headerHeight: Int, extraHeight: Int = 0) {
        itemView.measure(0, 0)
        if (headerHeight != itemView.measuredHeight + extraHeight) {
            doAnim(
                itemView,
                ValueAnimator.ofInt(headerHeight, itemView.measuredHeight + extraHeight)
            )
        }
    }


    fun doAnim(itemView: ViewGroup, valueAnimator: ValueAnimator) {
        valueAnimator.duration =
            itemView.context.resources.getInteger(R.integer.anim_duration_short).toLong()
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            itemView.layoutParams.height = value
            itemView.requestLayout()
        }

        valueAnimator.start()
    }
}