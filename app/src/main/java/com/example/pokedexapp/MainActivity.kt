package com.example.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import java.util.LinkedList

class MainActivity : AppCompatActivity() {
    private lateinit var urlList: MutableList<String>
    private lateinit var nameList: MutableList<String>
    private lateinit var typeList: MutableList<String>
    private lateinit var rvPokemon: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        urlList = mutableListOf()
        nameList = mutableListOf()
        typeList = mutableListOf()
        rvPokemon = findViewById(R.id.pokedexRecyclerView)

        var pokemonAdapter = PokemonAdapter(urlList, nameList, typeList)
        rvPokemon.adapter = pokemonAdapter
        rvPokemon.layoutManager = object : LinearLayoutManager(this@MainActivity) {
            override fun canScrollVertically(): Boolean {
                return findLastCompletelyVisibleItemPosition() < itemCount - 1
            }
        }
        val dividerItemDecoration = DividerItemDecoration(rvPokemon.context, LinearLayoutManager.VERTICAL)
        rvPokemon.addItemDecoration(dividerItemDecoration)

        getPokemonInfo(object : PokemonInfoCallback {
            override fun onSuccess(evoDict: MutableMap<String, String>) {}
            override fun onSuccess(evoUrl: String, dexEntry: String) {}
            override fun onSuccess(abilities: MutableMap<String, String>, height: Int, weight: Int) {}
            override fun onSuccess(urls: MutableList<String>, names: MutableList<String>, types: MutableList<String>) {
                urlList = urls
                nameList = names
                typeList = types
                pokemonAdapter.updateData(urlList, nameList, typeList)
            }
            override fun onFailure(error: String) {
            }
        })
    }
        private fun getPokemonInfo(callback: PokemonInfoCallback) {
            val urlInput = "https://pokeapi.co/api/v2/pokemon/?limit=1017"
            val client = AsyncHttpClient()
            client[urlInput, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                    val pokeJson = json.jsonObject.getJSONArray("results")
                    val queue = LinkedList<Int>()
                    for (i in 0 until pokeJson.length()) {
                        queue.add(i)
                    }
                    processQueue(queue, client, callback)
                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Pokemon Error", errorResponse)
                    callback.onFailure(errorResponse)
                }
            }]
        }

    private fun processQueue(queue: LinkedList<Int>, client: AsyncHttpClient, callback: PokemonInfoCallback) {
        if (queue.isEmpty()) {
            return
        }
        val i = queue.removeFirst()
        val urlInput = "https://pokeapi.co/api/v2/pokemon/${i+1}"
        urlList.add(urlInput)
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                var name = json.jsonObject.getString("name").replaceFirstChar{it.uppercase()}
                if (name.contains("-")) {
                    name = name.substring(0, name.indexOf("-"))
                }
                nameList.add(name)
                val pokeJson = json.jsonObject
                typeList.add(pokeJson.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                if (pokeJson.getJSONArray("types").length() > 1) {
                    typeList[typeList.size-1] += "|" + pokeJson.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name").replaceFirstChar{it.uppercase()}
                }
                callback.onSuccess(urlList, nameList, typeList)

                processQueue(queue, client, callback)
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
                callback.onFailure(errorResponse)
            }
        }]
    }

}

interface PokemonInfoCallback {
    fun onSuccess(evoDict : MutableMap<String, String>)
    fun onSuccess(evoUrl : String, dexEntry : String)
    fun onSuccess(urls : MutableList<String>, names: MutableList<String>, types: MutableList<String>)
    fun onSuccess(abilities : MutableMap<String, String>, height : Int, weight : Int)
    fun onFailure(error: String)
}