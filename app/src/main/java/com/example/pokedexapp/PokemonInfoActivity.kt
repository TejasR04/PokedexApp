package com.example.pokedexapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var url: String
    private lateinit var name: String
    private lateinit var types: String
    private lateinit var pokemonName : TextView
    private lateinit var pokedexNumber : TextView
    private lateinit var pokemonImage : ImageView
    private lateinit var pokemonType : TextView
    private lateinit var shinyButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_info)
        url = intent.getStringExtra("url").toString()
        name = intent.getStringExtra("name").toString()
        types = intent.getStringExtra("type").toString()
        Log.d("PokemonInfoActivity", url)
        pokemonName = findViewById(R.id.pokemonName)
        pokemonName.text = name
        pokedexNumber = findViewById(R.id.pokedexNumber)
        val zeroes = "0".repeat(4 - (url.substring(34, url.length)).length)
        pokedexNumber.text = "No." + zeroes + (url.substring(34, url.length))
        pokemonImage = findViewById(R.id.pokemonPicture)
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${url.substring(34, url.length)}.png")
            .into(pokemonImage)
        var shiny = false
        shinyButton = findViewById(R.id.shinyButton)
        shinyButton.setOnClickListener {
            if (shiny) {
                shiny = false
                Glide.with(this)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${url.substring(34, url.length)}.png")
                    .into(pokemonImage)
            } else {
                shiny = true
                Glide.with(this)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/${url.substring(34, url.length)}.png")
                    .into(pokemonImage)

            }
        }
        pokemonType = findViewById(R.id.types)
        pokemonType.text = types
    }
}