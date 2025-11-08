package br.edu.utfpr.calculaimc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.calculaimc.util.calcularImc
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btCalcular: Button
    private lateinit var btLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        btCalcular.setOnClickListener{
            btCalcularOnClick()
        }

        btLimpar.setOnClickListener{
            btLimparOnClick()
        }

        btLimpar.setOnLongClickListener{
            Toast.makeText( this, getString(R.string.limpar_long_click), Toast.LENGTH_SHORT ).show()
            true
        }
    } //fim do onCreate()



    private fun init(){
        etPeso = findViewById( R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        tvResultado = findViewById(R.id.tvResultado)
        btCalcular = findViewById(R.id.btCalcular)
        btLimpar = findViewById(R.id.btLimpar)
    }

    private fun btCalcularOnClick() {

        //entrada
        val peso = etPeso.text.toString().toDoubleOrNull()
        val altura = etAltura.text.toString().toDoubleOrNull()

        if ( peso == null ) {
            etPeso.error = getString(R.string.peso_error)
            return
        }

        if ( altura == null ) {
            //Toast.makeText( this, "Altura deve ser informado.", Toast.LENGTH_SHORT ).show()
            etAltura.error = getString(R.string.altura_error2)
            return
        }

        //processamento
        val locale = Locale.getDefault().language //recupera a linguagem do device (en, pt)
        //val countryCode = Locale.getDefault().country //recupera o país do device (US, BR)
        val resultado: Double = calcularImc(peso, altura, locale )


        //saída
        val nf = NumberFormat.getInstance( Locale.getDefault() )
        tvResultado.text = nf.format( resultado )
    }



    private fun btLimparOnClick() {
        etPeso.setText( "" )
        etAltura.setText( "" )
        tvResultado.text =  getString(R.string.zeros)
        etPeso.error = null
        etAltura.error = null
        etPeso.requestFocus()
    }


}//fim da MainActivity


