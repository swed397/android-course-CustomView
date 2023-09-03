package com.android.course.android_course_customview

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonUp: Button
    private lateinit var buttonDown: Button
    private lateinit var speedometer: Speedometer
    private lateinit var speedTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer = findViewById(R.id.speedometer)
        speedTextView =
            findViewById<TextView?>(R.id.speed_text_view).apply {
                text = speedometer.getSpeed().toString()
            }
        buttonUp = findViewById<Button?>(R.id.button_up).apply {
            setOnClickListener {
                speedometer.speedUp()
                speedTextView.text = speedometer.getSpeed().toString()
            }
        }
        buttonDown = findViewById<Button?>(R.id.button_down).apply {
            setOnClickListener {
                speedometer.speedDown()
                speedTextView.text = speedometer.getSpeed().toString()
            }
        }
    }
}