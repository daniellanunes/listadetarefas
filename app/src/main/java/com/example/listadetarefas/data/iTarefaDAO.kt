package com.example.listadetarefas.data

import com.example.listadetarefas.model.Tarefa

interface iTarefaDAO {
    fun salvar(tarefa: Tarefa): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun excluir(idTarefa: Int): Boolean
    fun listar(): List<Tarefa>
}