package br.edu.utfpr.usandogps_pm25s_2023_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import br.edu.utfpr.usandogps_pm25s_2023_2.adapter.MeuAdapter
import br.edu.utfpr.usandogps_pm25s_2023_2.database.DatabaseHandler

class ListarActivity : AppCompatActivity() {

    private lateinit var banco : DatabaseHandler
    private lateinit var lvPrincipal : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        banco = DatabaseHandler(this)

        lvPrincipal = findViewById(R.id.lvPrincipal)
    }

    override fun onStart() {
        super.onStart()
        val cursor = banco.listCursor()
        val adapter = MeuAdapter(this,cursor)
        lvPrincipal.adapter = adapter
    }

    //cria a intent e a leva para a outra tela do aplicativo toda vez que o botão incluir é acionado
    fun btIncluirOnClick(view: View) {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }
}