package com.example.listadetarefas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadetarefas.adapter.TarefaAdapter
import com.example.listadetarefas.data.TarefaDAO
import com.example.listadetarefas.databinding.ActivityMainBinding
import com.example.listadetarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity :: class.java)
            startActivity(intent)
        }
        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> editar(tarefa) }
        )
        //RecyclerView
        binding.rvTarefa.adapter = tarefaAdapter
        binding.rvTarefa.layoutManager = LinearLayoutManager(this)
    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)

        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")
        alertBuilder.setPositiveButton("Sim"){_,_ ->
            val tarefaDAO = TarefaDAO(this)
            if (tarefaDAO.excluir(id)){
                atualizarTarefas()
                Toast.makeText(this, "Tarefa removida com sucesso", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this, "Falha ao remover a tarefa", Toast.LENGTH_SHORT).show()

        }
        alertBuilder.setNegativeButton("Não"){_,_ ->}
        alertBuilder.create().show()
    }

    private fun atualizarTarefas(){
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)

    }
    override fun onStart() {
        super.onStart()
        atualizarTarefas()
    }
}