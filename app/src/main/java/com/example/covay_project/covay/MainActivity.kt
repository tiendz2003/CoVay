package com.example.covay_project.covay

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.covay_project.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnDouble :Button
    private lateinit var btnExit :Button
    private lateinit var btnRecord:Button
    private lateinit var btnSetting :Button
    private lateinit var btnSingle0 :Button
    private lateinit var btnSingle1:Button
    private lateinit var btnSingle2 :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
        val metrics:DisplayMetrics = resources.displayMetrics
        val i:Int = metrics.widthPixels
        val i2:Int = metrics.heightPixels-50
        this.btnSingle0 = findViewById(R.id.button1)
        this.btnSingle0.setOnClickListener {
            val intent = Intent(this@MainActivity,BoardActivity::class.java)
            val b = Bundle().apply {
                putInt("Type",0)
            }
            intent.putExtras(b)
            startActivity(intent)
        }
        this.btnSingle1 = findViewById(R.id.button2)
        this.btnSingle1.setOnClickListener {
            val intent = Intent(this@MainActivity,BoardActivity::class.java)
            val b = Bundle().apply {
                putInt("Type",1)
            }
            intent.putExtras(b)
            startActivity(intent)
        }
        this.btnSingle2 = findViewById(R.id.button3)
        this.btnSingle2.setOnClickListener {
            val intent = Intent(this@MainActivity,BoardActivity::class.java)
            val b = Bundle().apply {
                putInt("Type",2)
            }
            intent.putExtras(b)
            startActivity(intent)
        }
        this.btnDouble = findViewById(R.id.button4)
        this.btnDouble.setOnClickListener {
            val intent = Intent(this@MainActivity,BoardActivity::class.java)
            val b = Bundle().apply {
                putInt("Type",-1)
            }
            intent.putExtras(b)
            startActivity(intent)
        }
        this.btnRecord = findViewById(R.id.button5)
        this.btnRecord.setOnClickListener {
            val intent = Intent(this@MainActivity,RecordActivity::class.java)
            intent.putExtras(Bundle())
            startActivity(intent)
        }
        this.btnExit = findViewById(R.id.button6)
        this.btnExit.setOnClickListener {
            finish()
        }
    }
}

