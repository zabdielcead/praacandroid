package com.cead.androiduno

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById(R.id.txtViewIntent) as TextView

        val bundle = intent.extras
        if (bundle != null && bundle.getString("saludo") != null ){
            val saludo = bundle.getString("saludo")
            textView.text = saludo
        }else{
            Toast.makeText(this, "no saludo", Toast.LENGTH_LONG).show()
        }
    }
}
