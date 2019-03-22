package com.cead.androiduno

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val LOGTAG = "LOGONE"
    val SALUDO =  "HOLA DESDE EL ACTIVITY MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this,"Primer activity", Toast.LENGTH_LONG).show()

        btnCalcular.setOnClickListener{
            val anioNacimiento:Int = editText.text.toString().toInt()
            val anioActual = Calendar.getInstance().get(Calendar.YEAR)
            val miEdad = anioActual - anioNacimiento
            textView.text = "Tu edad es $miEdad años"
        }

        btnSiguiente.setOnClickListener{
            startActivity(this, SecondActivity:: class.java)
        }

    }

    fun startActivity(activity: Activity, nextActivity: Class<*> ){ //intent explicito porque ya sabemos como se va  acomportar
        val intent = Intent(activity, nextActivity)
        intent.putExtra("saludo",SALUDO)
        activity.startActivity(intent)
        activity.finish()
    }

/*
    override fun onStart() {
        super.onStart()
        Log.i(LOGTAG,"onstart")
        Toast.makeText(this,"onStart", Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()
        Log.i(LOGTAG,"onresume")
        Toast.makeText(this,"onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOGTAG,"onpause")
        Toast.makeText(this,"onPause", Toast.LENGTH_SHORT).show()
    }


    override fun onStop() {
        super.onStop()
        Log.i(LOGTAG,"onstop")
        Toast.makeText(this,"onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOGTAG,"onRestart")
        Toast.makeText(this,"onRestart", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOGTAG,"onDestroy")
        Toast.makeText(this,"onDestroy", Toast.LENGTH_SHORT).show()
    }

    */




}
