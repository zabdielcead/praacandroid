package com.cead.androiduno

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.layout_actionbar.*


class ThirdActivity : AppCompatActivity() {

    private val PHONECODE = 4000 //Puede ser cualquier numero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        setSupportActionBar(actionBars)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // -- boton para la llamada
        imageButtonPhone!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val phoneNumber = editTextPhone!!.text.toString()
                if(!phoneNumber.isEmpty()){
                    //a partir del api 23 o posterior deben salir las alertas de permisos al usuario
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checarPermiso(Manifest.permission.CALL_PHONE)){
                            val intentAcpetado = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
                            if(ActivityCompat.checkSelfPermission(this@ThirdActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return
                            }
                            startActivity(intentAcpetado)
                        }else{
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){ // si nunk se le ha preguntado al usuario manda el metodo asincrono saldra popup
                                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),PHONECODE)
                            }else{
                                Toast.makeText(this@ThirdActivity,"Habilita el permiso para continuar", Toast.LENGTH_LONG).show()
                                val intentSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSettings.data = Uri.parse("package:"+ packageName)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                startActivity(intentSettings)
                            }
                        }
                    }else{
                        versionAntigua(phoneNumber)
                    }

                }else{
                    Toast.makeText(this@ThirdActivity, "Introduce un numero ", Toast.LENGTH_LONG).show()
                }
            }

        })


        //--  boton para la web
        imageButton2!!.setOnClickListener{
            val url = editTextWeb!!.text.toString()
            val intentWeb = Intent()
            intentWeb.action = Intent.ACTION_VIEW
            intentWeb.data = Uri.parse("http://"+ url)
            startActivity(intentWeb)
        }

        //-- boton para correo
        buttonEmailMe!!.setOnClickListener{
            val email = "zabdiel.cead@gmail.com"
            val intentEmail = Intent(Intent.ACTION_SEND, Uri.parse(email))
            intentEmail.type = "plain/text"
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Titulo Email")
            intentEmail.putExtra(Intent.EXTRA_TEXT,"Hola estoy esperando la respuesta....")
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("qelderesaltatension@gmail.com","abibarte@gmail.com"))
            startActivity(Intent.createChooser(intentEmail,"Elige cliente de correo")) //para que el usuario elija el proveedor de correo electronico
        }

        //boton para la llamada sin permisos
        buttonContactPhone!!.setOnClickListener{
            val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:999900123"))
            startActivity(intentCall)
        }


        //-- boton para la camara
        imageButtonCamera!!.setOnClickListener{
            val intentCamera = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intentCamera)
        }


    }
    //-- contactos
    override fun onCreateOptionsMenu(menu: Menu):Boolean{ // aprezca el layout menu
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean { //el usuario click una opcion del menu
        when (item!!.itemId){
            R.id.menuContactos ->{
                val intentContactos = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
                startActivity(intentContactos)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    // metodo asincrono para comprobar permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode){
            PHONECODE -> {
                val permiso = permissions[0]
                val resultado = grantResults[0]

                if(permiso == Manifest.permission.CALL_PHONE){
                    // -- comprobar si ha sido aceptado o denegado la peticion de permiso
                        if(resultado == PackageManager.PERMISSION_GRANTED){ // cuando se da un ok en la alerta en el permiso
                            // -- concedio su permiso
                                val phoneNumber =  editTextPhone!!.text.toString()
                                val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
                            // -- debemos verificar que exista el permiso en el manifest explicitamente
                            // ya que el usuario puede rechazar esta peticion de permiso
                                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return
                                }
                                startActivity(intentCall)
                        }else {
                            //-- denego el permiso
                                Toast.makeText(this,"ha denegado el permiso", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else ->   super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }




    }

    fun checarPermiso(permission: String): Boolean{
        val result = this.checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun versionAntigua(phoneNumber: String){
        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
        if(checarPermiso(Manifest.permission.CALL_PHONE)){
            if(ActivityCompat.checkSelfPermission(this@ThirdActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            startActivity(intentCall)
        }
    }
}
