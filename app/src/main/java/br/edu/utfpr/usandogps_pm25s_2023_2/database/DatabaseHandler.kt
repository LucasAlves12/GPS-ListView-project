package br.edu.utfpr.usandogps_pm25s_2023_2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandogps_pm25s_2023_2.entity.Lugar

class DatabaseHandler ( context : Context) : SQLiteOpenHelper( context, DATABASE_NAME, null, DATABASE_VERSION ){
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "dbfile.sqlite"
        private val TABLE_NAME = "lugares"
        private val KEY_ID = "_id"
        private val KEY_LATITUDE = "latitude"
        private val KEY_DESCRICAO = "descricao"
        private val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(bd: SQLiteDatabase?) {
        bd?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${KEY_DESCRICAO} TEXT, ${KEY_LATITUDE} TEXT, ${KEY_LONGITUDE} TEXT)" )
    }

    override fun onUpgrade(bd: SQLiteDatabase?, p1: Int, p2: Int) {
        bd?.execSQL( "DROP TABLE ${TABLE_NAME}" )
        onCreate( bd )
    }

    fun insert( lugar : Lugar) {
        val registro = ContentValues()
        registro.put( KEY_DESCRICAO, lugar.descricao)
        registro.put( KEY_LATITUDE, lugar.latitute )
        registro.put(KEY_LONGITUDE, lugar.longitude)

        val bd = this.writableDatabase
        bd.insert( TABLE_NAME, null, registro )
    }

    fun update( lugar: Lugar) {
        val registro = ContentValues()
        registro.put( KEY_DESCRICAO, lugar.descricao )
        registro.put( KEY_LATITUDE, lugar.latitute )
        registro.put(KEY_LONGITUDE, lugar.longitude)

        val bd = this.writableDatabase
        bd.update( TABLE_NAME, registro, "_id=${lugar._id}", null )
    }

    fun delete( _id : Int) {
        val bd = this.writableDatabase
        bd.delete( TABLE_NAME, "_id=${_id}", null )
    }

    //retorna lista de objetos do banco de dados
    fun list() : MutableList<Lugar> {
        val bd = this.writableDatabase

        val cursor = bd.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val registros = mutableListOf<Lugar>()

        while ( cursor.moveToNext() ) {
            val lugar = Lugar( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ),cursor.getString(3) )
            registros.add( lugar )
        }

        return registros
    }

    // Retorna um cursor contendo os registros do banco de dados.
    fun listCursor() : Cursor {
        val bd = this.writableDatabase

        val cursor = bd.query( TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return cursor
    }
}