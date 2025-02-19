package com.example.listadetarefas.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, NOME_BANCO_DADOS, null, VERSAO) {


    companion object{
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val VERSAO = 1
        const val NOME_TABELA_TAREFA = "tarefa"
        const val COLUNA_ID_TAREFA = "id_tarefa"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA = "data"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql="CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFA (" +
                "$COLUNA_ID_TAREFA INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUNA_DESCRICAO VARCHAR (10)," +
                "$COLUNA_DATA DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"
        try {
            db?.execSQL(sql)
            Log.i("info_db", "Banco de dados criado com sucesso!")

        }catch (e:Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar Banco de dados")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}