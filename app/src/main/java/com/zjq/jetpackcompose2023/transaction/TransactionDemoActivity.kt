package com.zjq.jetpackcompose2023.transaction

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import com.zjq.jetpackcompose2023.R

class TransactionDemoActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_demo)
        val start = findViewById<View>(R.id.onePage)
        val end = findViewById<View>(R.id.twoPage)
        val tvStart = findViewById<TextView>(R.id.tv_one)
        val tvEnd = findViewById<TextView>(R.id.tv_two)
        val screenWidth = screenWidth()
        end.translationX = screenWidth.toFloat()
        start.setOnClickListener {
            startAnima(0, screenWidth, onChange = {
                startTransaction(start, end, it)
            }, onEnd = {

            })
        }

        end.setOnClickListener {
            startAnima(0, screenWidth, onChange = {
                endTransaction(start, end, it)
            }, onEnd = {

            })
        }
        tvStart.setOnClickListener {
            Toast.makeText(this, "click pageOne", Toast.LENGTH_SHORT).show()
        }
        tvEnd.setOnClickListener {
            Toast.makeText(this, "click pageTwo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTransaction(start: View, end: View, translateX: Int) {
        start.translationX = -translateX.toFloat()
        end.translationX = (screenWidth() -  translateX).toFloat()
    }


    private fun endTransaction(start: View, end: View, translateX: Int) {
        start.translationX = (-screenWidth() + translateX).toFloat()
        end.translationX = translateX.toFloat()

    }
    var animator:ValueAnimator ?= null
    private fun startAnima(start: Int, end: Int, onChange:(Int)-> Unit, onEnd:() -> Unit) {
        if (animator?.isRunning == true) {
            return
        }
        animator = ValueAnimator.ofInt(start, end).apply {
            if (isRunning) {
                return@apply
            }
            duration = 500

            addUpdateListener {
                val value = it.animatedValue as Int
                onChange(value)
            }
            addListener {
                doOnEnd {
                    onEnd()
                }
            }
            interpolator = LinearInterpolator()
            start()
        }

    }


    fun screenWidth(): Int {
        return (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width
    }
}