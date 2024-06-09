package com.example.covay_project.covay

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.covay_project.R
import com.example.covay_project.covay.PlayMode
import com.example.covay_project.covay.Record
import com.example.covay_project.covay.Recordetail

class RecordActivity:AppCompatActivity() {
    private lateinit var tv32:TextView
    private lateinit var tv33:TextView
    private lateinit var tv34:TextView
    private lateinit var tv42:TextView
    private lateinit var tv43:TextView
    private lateinit var tv44:TextView
    private lateinit var tv52:TextView
    private lateinit var tv53:TextView
    private lateinit var tv54: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_acitivity)
        val rd01:Recordetail = Record.getRecord(this,PlayMode.SINGLE_0)
        val rd02:Recordetail = Record.getRecord(this,PlayMode.SINGLE_1)
        val rd03:Recordetail = Record.getRecord(this,PlayMode.SINGLE_2)
        this.tv32 =  findViewById(R.id.textView32);
        this.tv33 =  findViewById(R.id.textView33);
        this.tv34 =  findViewById(R.id.textView34);
        this.tv42 =  findViewById(R.id.textView42);
        this.tv43 =  findViewById(R.id.textView43);
        this.tv44 =  findViewById(R.id.textView44);
        this.tv52 =  findViewById(R.id.textView52);
        this.tv53 =  findViewById(R.id.textView53);
        this.tv54 =  findViewById(R.id.textView54);

        tv32.text = rd01.win.toString()
        tv33.text = rd01.lose.toString()
        tv34.text = rd01.giveup.toString()

        tv42.text = rd02.win.toString()
        tv44.text = rd02.lose.toString()
        tv44.text = rd02.giveup.toString()

        tv52.text = rd03.win.toString()
        tv53.text = rd03.lose.toString()
        tv54.text = rd03.giveup.toString()
    }
}