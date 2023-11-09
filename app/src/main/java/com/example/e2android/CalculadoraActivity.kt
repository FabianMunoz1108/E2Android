package com.example.e2android

import Modelo.CalculadoraModelo
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalculadoraActivity:AppCompatActivity() {
    private lateinit var model: CalculadoraModelo
    private lateinit var resultAdapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calculadora_activity)

        model = CalculadoraModelo()
        setupRecyclerView()

        val n1 :EditText = findViewById(R.id.numero1)
        val n2 :EditText = findViewById(R.id.numero2)
        val btn: Button = findViewById(R.id.addButton)

        btn.setOnClickListener{
            val numero1 = n1.text.toString().toIntOrNull() ?: 0
            val numero2 = n2.text.toString().toIntOrNull() ?: 0

            val resultado = model.add(numero1, numero2)
            resultAdapter.addResult(resultado)
        }
    }
    fun setupRecyclerView(){
     val resultRecyclerview: RecyclerView = findViewById(R.id.resultsRecyclerView)
        resultRecyclerview.layoutManager = LinearLayoutManager(this)
        resultAdapter = ResultsAdapter(model.getResults().toMutableList())
        resultRecyclerview.adapter = resultAdapter
    }
}
class ResultsAdapter(private val results: MutableList<String>) : RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>(){
    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val resulTextView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.resulTextView.text = results[position].toString()
    }

    override fun getItemCount() = results.size

    fun addResult(result:String)
    {
        results.add(result)
        notifyItemInserted(results.size - 1)
    }
}