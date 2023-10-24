package com.example.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var pokemonImageURL = ""
    var pokedexNumber = 0
    var name = ""
    var weight = ""
    private lateinit var pokemonList: MutableList<String>
    private lateinit var pokemonImageList: MutableList<String>
    private lateinit var nameList: MutableList<String>
    private lateinit var weightList: MutableList<String>
    private lateinit var rvPokemon: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pokemonList = mutableListOf()
        pokemonImageList = mutableListOf()
        nameList = mutableListOf()
        weightList = mutableListOf()
        rvPokemon = findViewById(R.id.pokedexRecyclerView)
        var pokemonAdapter = PokemonAdapter(pokemonImageList, nameList, weightList)
        rvPokemon.adapter = pokemonAdapter
        rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
        val dividerItemDecoration = DividerItemDecoration(rvPokemon.context, LinearLayoutManager.VERTICAL)
        rvPokemon.addItemDecoration(dividerItemDecoration)
        for (i in 0..49) {
            pokedexNumber = (1..1017).random()
            getPokemonInfo(pokedexNumber, object : PokemonInfoCallback {
                override fun onSuccess(pokemonImageURL: String, name: String, weight: String) {
                    pokemonImageList.add(pokemonImageURL)
                    nameList.add(name)
                    weightList.add(weight)
                    pokemonAdapter.updateData(pokemonImageList, nameList, weightList)
                    Log.d("please", weight)
                    Log.d("finally0", name)
                    Log.d("finally1",nameList.elementAt(nameList.size - 1))
                    Log.d("finally2",pokemonImageURL)
                    Log.d("finally3",pokedexNumber.toString())
                }
                override fun onFailure(error: String) {
                }
            })
        }
    }
    private fun getPokemonInfo(pokedexNumber: Int, callback: PokemonInfoCallback) {
        val urlInput = "https://pokeapi.co/api/v2/pokemon/$pokedexNumber"
        pokemonList.add(urlInput)
        val client = AsyncHttpClient()
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                pokemonImageURL =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokedexNumber.png"
                name = json.jsonObject.getString("name").replaceFirstChar{it.uppercase()}
                weight = json.jsonObject.getString("weight")
                weight = (weight.toDouble() / 10).toString() + " kg"
                Log.d("Pokemon", "response successful$json")
                callback.onSuccess(pokemonImageURL, name, weight)
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
    interface PokemonInfoCallback {
        fun onSuccess(pokemonImageURL: String, name: String, weight: String)
        fun onFailure(error: String)
    }
}
