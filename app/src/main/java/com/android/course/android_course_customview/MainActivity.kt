package com.android.course.android_course_customview

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonUp: Button
    private lateinit var buttonDown: Button
    private lateinit var speedometer: Speedometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometer = findViewById(R.id.speedometer)
        buttonUp = findViewById<Button?>(R.id.button_up).apply {
            setOnClickListener {
                speedometer.speedUp()
            }
        }
        buttonDown = findViewById<Button?>(R.id.button_down).apply {
            setOnClickListener {
                speedometer.speedDown()
            }
        }
    }
}