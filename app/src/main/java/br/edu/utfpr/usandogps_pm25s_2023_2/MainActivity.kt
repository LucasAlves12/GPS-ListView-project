package br.edu.utfpr.usandogps_pm25s_2023_2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import br.edu.utfpr.usandogps_pm25s_2023_2.database.DatabaseHandler
import br.edu.utfpr.usandogps_pm25s_2023_2.entity.Lugar
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var etCod : EditText
    private lateinit var etDescricao : EditText
    private lateinit var tvLatitude : TextView
    private lateinit var tvLongitude : TextView
    private lateinit var banco : DatabaseHandler

    private lateinit var locationManager : LocationManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtém uma referência ao LocationManager, que é usado para acessar serviços
        // de localização no dispositivo.
        locationManager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager

        // Verifica se as permissões de localização fina e grosseira não foram concedidas.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission. ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0f, this )

        tvLatitude = findViewById( R.id.tvLatitude )
        tvLongitude = findViewById( R.id.tvLongitude )
        etCod = findViewById(R.id.etCod)
        etDescricao = findViewById(R.id.etDescricao)

        banco = DatabaseHandler(this)

        if ( intent.getIntExtra( "cod",0 ) != 0 ) {//verifica se tem dados passados pelo intent
            val cod = intent.getIntExtra("cod", 0)
            val descricao = intent.getStringExtra("descricao")
            val latitude = intent.getStringExtra("latitude")
            val longitude = intent.getStringExtra("longitude")


            //se houver preenche com os devidos dados
            etCod.setText( cod.toString() )
            etDescricao.setText( descricao )
            tvLatitude.setText( latitude )
            tvLongitude.setText( longitude )

        }
        else {
            val btExcluir = findViewById<Button>( R.id.btExcluir )

            //caso não houver dados o botão excluir fica invisivel

            btExcluir.visibility = View.GONE

        }

        banco = DatabaseHandler(this)
    }



    //método chamado quando a localização do dispositivo muda
    override fun onLocationChanged(position: Location) {
        tvLatitude.setText( position.latitude.toString() )
        tvLongitude.setText( position.longitude.toString() )
    }


    fun btAlterarOnClick(view: View) {
        if ( etCod.text.toString().isEmpty() ) {//inserção no banco de dados
            val lugar = Lugar( 0, etDescricao.text.toString(), tvLatitude.text.toString(), tvLongitude.text.toString() )
            banco.insert( lugar )
            Toast.makeText( this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT ).show()
            limparTela()
        } else {//edição do banco de dados
            val pessoa = Lugar( etCod.text.toString().toInt(), etDescricao.text.toString(), tvLatitude.text.toString(), tvLongitude.text.toString() )
            banco.update( pessoa )
            Toast.makeText( this, "Alteração realizada com sucesso", Toast.LENGTH_SHORT ).show()
        }

        finish()
    }

    fun btExcluirOnClick(view: View) {//excluir lugar do banco e da lista

        banco.delete( etCod.text.toString().toInt() )

        Toast.makeText( this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT ).show()

        finish()
    }

    fun limparTela() {
        etCod.setText( "" )
        etDescricao.setText( "" )
    }

}