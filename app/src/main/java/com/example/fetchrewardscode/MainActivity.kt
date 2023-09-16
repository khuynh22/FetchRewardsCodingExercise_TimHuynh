package com.example.fetchrewardscode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val itemsMap = mutableMapOf<Int, MutableList<String>>() // Map listId to names

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the title text
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = "Fetch Rewards"

        fetchData()
    }

    // Fetch data from the provided URL and process it
    private fun fetchData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val jsonData = response.body?.string()

                jsonData?.let {
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val listId = jsonObject.optInt("listId")
                        val name = jsonObject.optString("name", null)

                        if (name != "null" && !name.isNullOrEmpty()) {
                            itemsMap.getOrPut(listId) { mutableListOf() }.add(name)
                        }
                    }

                    // Sort the items within each listId group based on index 5
                    // to the last index of the name
                    // (sort by the number part in "Item: number")
                    itemsMap.forEach { (_, names) ->
                        names.sortBy { it.substring(5).toInt() }
                    }
                    updateUI()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the user interface with the processed data
    private fun updateUI() {
        runOnUiThread {
            val adapter = ItemAdapter(itemsMap)
            recyclerView.adapter = adapter
        }
    }
}