package com.example.listadetarefas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.listadetarefas.databinding.ItemTarefaBinding
import com.example.listadetarefas.model.Tarefa

class TarefaAdapter(val onCliqueExcluir:(Int) -> Unit, val onCliqueEditar:(Tarefa) -> Unit) : Adapter<TarefaAdapter.TarefaViewHolder>(){

    private var listaTarefa: List<Tarefa> = emptyList()

    fun adicionarLista(lista :List<Tarefa>){
        this.listaTarefa= lista
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(itemBinding : ItemTarefaBinding) : ViewHolder(itemBinding.root){


        private val binding: ItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind (tarefa: Tarefa){
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCadastro

            binding.btnExcluir.setOnClickListener {
                onCliqueExcluir(tarefa.idTarefa)
            }
            binding.btnEditar.setOnClickListener {
                onCliqueEditar(tarefa)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemTarefaBinding.inflate(layoutInflater, parent, false)
        return TarefaViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefa[position]
        holder.bind(tarefa)
    }
    override fun getItemCount(): Int {
        return listaTarefa.size
    }
}