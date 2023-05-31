package com.seoultech.healthytoday

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class TargetActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val fastfood = readCSVFile(applicationContext, "fastfood.csv")
        Log.d("IISE",fastfood[0].toString())

        adapter = MyAdapter(fastfood)
        recyclerView.adapter = adapter
    }
}


class MyAdapter(private val dataList: List<List<String>>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.titleTextView.text = data[0]
        holder.descriptionTextView.text = data[1]
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
fun readCSVFile(context: Context, fileName: String): List<List<String>> {
    val dataList: MutableList<List<String>> = mutableListOf()

    try {
        val inputStream: InputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val data: List<String> = line!!.split(",").map { it.trim() }
            dataList.add(data)
        }

        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return dataList
}
