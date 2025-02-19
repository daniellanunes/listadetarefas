package com.example.listadetarefas.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.listadetarefas.model.Tarefa
import com.example.listadetarefas.data.DatabaseHelper.Companion.COLUNA_DATA
import com.example.listadetarefas.data.DatabaseHelper.Companion.COLUNA_DESCRICAO
import com.example.listadetarefas.data.DatabaseHelper.Companion.COLUNA_ID_TAREFA
import com.example.listadetarefas.data.DatabaseHelper.Companion.NOME_TABELA_TAREFA
class TarefaDAO (context: Context) : iTarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)

        try {
            escrita.insert(DatabaseHelper.NOME_TABELA_TAREFA, null, conteudos)
            Log.i("info_db", "Tarefa adicionada no Banco de Dados")

        }catch (e:Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao adicionar no Banco de Dados")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudos = ContentValues()
        conteudos.put("$COLUNA_DESCRICAO", tarefa.descricao)
        try {
            escrita.update("$NOME_TABELA_TAREFA", conteudos, "$COLUNA_ID_TAREFA = ?", args )
            Log.i("info_db", "Sucesso ao Atualizar tarefa")

        }catch (e:Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar no Banco de Dados")
            return false
        }
        return true
    }

    override fun excluir(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete("$NOME_TABELA_TAREFA", "$COLUNA_ID_TAREFA = ?", args)
            Log.i("info_db", "Sucesso ao remover tarefa")

        }catch (e:Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover no Banco de Dados")
            return false
        }
        return true
    }

    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()

        val sql = "SELECT $COLUNA_ID_TAREFA, $COLUNA_DESCRICAO, strftime('%d/%m/%Y', $COLUNA_DATA) AS $COLUNA_DATA FROM $NOME_TABELA_TAREFA "

        val cursor = leitura.rawQuery(sql,null)
        val indiceIdTarefa = cursor.getColumnIndex(COLUNA_ID_TAREFA)
        val indiceDescricao = cursor.getColumnIndex(COLUNA_DESCRICAO)
        val indiceData = cursor.getColumnIndex(COLUNA_DATA)

        while (cursor.moveToNext()){
            val idList = cursor.getInt(indiceIdTarefa)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)
            listaTarefas.add(
                Tarefa(idList,descricao,data)
            )
            Log.d("info_db", "$idList, $descricao, $data")
        }
        cursor.close()
        return listaTarefas
    }
}
