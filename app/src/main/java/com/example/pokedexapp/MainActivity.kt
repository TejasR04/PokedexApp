package com.example.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var pokemonImageURL = ""
    var pokedexNumber = 0
    var name = ""
    var weight = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPokemonImageURL()
        Log.d("pokemonImageURL", "pokemon image url set")
        var button = findViewById<Button>(R.id.pokemonButton)
        var imageView = findViewById<ImageView>(R.id.pokemonImage)
        var nameText = findViewById<TextView>(R.id.pokemonName)
        var weightText = findViewById<TextView>(R.id.pokemonWeight)
        getNextPokemon(button, imageView, nameText, weightText)
    }
    private fun getPokemonImageURL() {
        val client = AsyncHttpClient()
        pokedexNumber = (1..1017).random()
        var urlInput = "https://pokeapi.co/api/v2/pokemon/$pokedexNumber"
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                pokemonImageURL =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokedexNumber.png"
                name = json.jsonObject.getString("name").replaceFirstChar{it.uppercase()}
                weight = json.jsonObject.getString("weight")
                weight = (weight.toDouble() / 10).toString() + " kg"
                Log.d("pokeName", name)
                Log.d("Pokemon", "response successful$json")
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }
    private fun getNextPokemon(button: Button, imageView: ImageView, nameText: TextView, weightText: TextView) {
        button.setOnClickListener {
            getPokemonImageURL()
            Glide.with(this)
                .load(pokemonImageURL)
                .fitCenter()
                .into(imageView)
            nameText.text = name
            weightText.text = weight
        }
    }
}