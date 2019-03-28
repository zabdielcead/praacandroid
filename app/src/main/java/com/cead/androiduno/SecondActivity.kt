package com.cead.androiduno

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.layout_actionbar.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setSupportActionBar(actionBars)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val textView = findViewById(R.id.txtViewIntent) as TextView

        val bundle = intent.extras
        if (bundle != null && bundle.getString("saludo") != null ){
            val saludo = bundle.getString("saludo")
            textView.text = saludo
        }else{
            Toast.makeText(this, "no saludo", Toast.LENGTH_LONG).show()
        }
        btnnext.setOnClickListener{
            startActivity(this, ThirdActivity::class.java)
        }
    }

    fun  startActivity(activity: Activity, nextActivity: Class<*>){
        val intent = Intent(activity, nextActivity)
        activity.startActivity(intent)
        //activity.finish()
    }
}
