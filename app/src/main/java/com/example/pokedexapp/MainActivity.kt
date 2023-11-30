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
    private lateinit var imageList: MutableList<String>
    private lateinit var typeList: MutableList<String>
    private lateinit var rvPokemon: RecyclerView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        urlList = mutableListOf()
        nameList = mutableListOf()
        imageList = mutableListOf()
        typeList = mutableListOf()
        rvPokemon = findViewById(R.id.pokedexRecyclerView)

        var pokemonAdapter = PokemonAdapter(urlList, nameList, typeList, imageList)
        rvPokemon.adapter = pokemonAdapter
        rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
        val dividerItemDecoration = DividerItemDecoration(rvPokemon.context, LinearLayoutManager.VERTICAL)
        rvPokemon.addItemDecoration(dividerItemDecoration)

        getPokemonInfo(object : PokemonInfoCallback {
            override fun onSuccess(urls: MutableList<String>, names: MutableList<String>, images: MutableList<String>, types: MutableList<String>) {
                urlList = urls
                nameList = names
                imageList = images
                typeList = types
                pokemonAdapter.updateData(urlList, nameList, imageList, typeList)
                // THIINK ABOUT TEMPORARY ARRAYS SO THAT THE RECYCLERVIEW DOESNT HAVE TO UPDATE DATA THATS ALREADY BEEN MADE
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
        count++
        val i = queue.removeFirst()
        val urlInput = "https://pokeapi.co/api/v2/pokemon/${i+1}"
        urlList.add(urlInput)
        imageList.add("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${i+1}.png")
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                var name = json.jsonObject.getString("name").replaceFirstChar{it.uppercase()}
                if (name.contains("-")) {
                    name = name.substring(0, name.indexOf("-"))
                }
                nameList.add(name)
                val pokeJson = json.jsonObject
                typeList.add(pokeJson.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name"))
                if (pokeJson.getJSONArray("types").length() > 1) {
                    typeList[typeList.size-1] += "|" + pokeJson.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name").replaceFirstChar{it.uppercase()}
                }
                if (count == 10) {
                    callback.onSuccess(urlList, nameList, imageList, typeList)
                    count = 0
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

}

interface PokemonInfoCallback {
    fun onSuccess(urls : MutableList<String>, names: MutableList<String>, images: MutableList<String>, types: MutableList<String>)
    fun onFailure(error: String)
}