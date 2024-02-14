package br.edu.utfpr.usandogps_pm25s_2023_2.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.usandogps_pm25s_2023_2.MainActivity
import br.edu.utfpr.usandogps_pm25s_2023_2.R
import br.edu.utfpr.usandogps_pm25s_2023_2.entity.Lugar

private const val COD = 0
private const val DESCRICAO = 1
private const val LATITUTE = 2
private const val LONGITUDE = 3

class MeuAdapter (val context: Context, val cursor : Cursor) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(id: Int): Any {
        cursor.moveToPosition( id )
        val lugar = Lugar( cursor.getInt(COD), cursor.getString(DESCRICAO), cursor.getString(
            LATITUTE), cursor.getString(LONGITUDE) )
        return lugar
    }

    override fun getItemId(id: Int): Long {
        cursor.moveToPosition( id )
        return cursor.getInt(COD).toLong()
    }

    override fun getView(id: Int, p1: View?, p2: ViewGroup?): View  {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elementos,null)

        val tvLugarElementoLista = v.findViewById<TextView>(R.id.tvLugarElementoLista)
        val tvLatitudeElementoLista = v.findViewById<TextView>(R.id.tvLatitudeElementoLista)
        val tvLongitudeElementoLista = v.findViewById<TextView>(R.id.tvLongitudeElementoLista)
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        cursor.moveToPosition(id)

        tvLugarElementoLista.text = cursor.getString(DESCRICAO)
        tvLatitudeElementoLista.text = cursor.getString(LATITUTE)
        tvLongitudeElementoLista.text = cursor.getString(LONGITUDE)

        btEditarElementoLista.setOnClickListener {
            cursor.moveToPosition(id)
            val intent = Intent( context, MainActivity::class.java)
            intent.putExtra( "cod", cursor.getInt(COD))
            intent.putExtra( "descricao", cursor.getString(DESCRICAO))
            intent.putExtra( "latitude", cursor.getString(LATITUTE))
            intent.putExtra( "longitude", cursor.getString(LONGITUDE))
            context.startActivity( intent )
            //Toast.makeText(context, "Item ${id} pressionado", Toast.LENGTH_SHORT).show()
        }

        return v
    }

}