package mx.edu.ittepic.tpdm_u2_practica3_15401020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var n : EditText?=null
    var m : EditText?=null
    var boton : Button?=null
    var boton2 : Button?=null
    var etiqueta : TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        n = findViewById(R.id.n)
        m = findViewById(R.id.m)
        boton = findViewById(R.id.boton)
        boton2 = findViewById(R.id.boton2)
        etiqueta = findViewById(R.id.etiqueta)

        boton?.setOnClickListener{
          etiqueta?.setText("")
          GenerarNumeros(n?.text.toString(), m?.text.toString(),applicationContext).execute()
        }
        boton2?.setOnClickListener{
            val abrirArchivo = BufferedReader(InputStreamReader(openFileInput("primos.txt")))  //buffered permite leer una cadena de texto
            var cadena = ""
            cadena = abrirArchivo.readLine()
            etiqueta?.setText(cadena)
        }
    }

    class GenerarNumeros(n: String, m: String, context: Context) : AsyncTask<Void, Void, List<Int>>(){ //doInBackGround,
        var n = n.toInt()
        var m = m.toInt()
        var c = context

        override fun doInBackground(vararg p0: Void?): List<Int>? {
            val gen = List(2000){ Random.nextInt(n,m)}
            println(gen)

            return gen
        }
        /*override fun onPreExecute() {
            super.onPreExecute()
        }*/
        override fun onPostExecute(result: List<Int>?) {
            super.onPostExecute(result)
            var cont = 0
            var et=""
            var numerosPrimos="Números primos:  "
            (0..1999).forEach {
                cont=0
                et=result?.get(it).toString()
                (1..et.toInt()).forEach {
                    if (et.toInt() % it == 0) {
                        cont++
                    }
                }
                if (cont <= 2 && et.toInt()>1) {
                    numerosPrimos=numerosPrimos+et+", "
                } else {
                   // println("$et -> no es primo")
                }
            }
            println(numerosPrimos)
            guardar(numerosPrimos)

        }

        fun guardar(numerosPrimos:String){
            val guardarArchivo = OutputStreamWriter(c.openFileOutput("primos.txt", Activity.MODE_PRIVATE)) //construye un archivo en memoria interna o externa, output para guardar
            guardarArchivo.write(numerosPrimos)
            guardarArchivo.flush() //para asegurarse que se guarde
            guardarArchivo.close()
            //println("GUARDADOOOOOOO")
            Toast.makeText(c,"¡Números generados y guardados correctamente!",Toast.LENGTH_LONG).show()
        }
    }


}
