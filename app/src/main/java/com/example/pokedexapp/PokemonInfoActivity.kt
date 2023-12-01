package com.example.pokedexapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var url: String
    private lateinit var name: String
    private lateinit var types: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_info)
        url = intent.getStringExtra("url").toString()
        name = intent.getStringExtra("name").toString()
        types = intent.getStringExtra("type").toString()
        Log.d("PokemonInfoActivity", url)

    }
}