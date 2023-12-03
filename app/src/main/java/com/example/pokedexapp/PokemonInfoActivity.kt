package com.example.pokedexapp

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide


class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var url: String
    private lateinit var name: String
    private lateinit var types: String
    private lateinit var pokemonName : TextView
    private lateinit var pokedexNumber : TextView
    private lateinit var pokemonImage : ImageView
    private lateinit var pokemonType1 : TextView
    private lateinit var pokemonType2 : TextView
    private lateinit var shinyButton : Button
    private lateinit var frame : FrameLayout

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
        pokemonType1 = findViewById(R.id.type1)
        frame = findViewById(R.id.frame1)
        var color : Int = 0
        var temp = types
        if (types.contains("|")) {
            temp = types.substring(0, types.indexOf("|"))
        }
        when (temp) {
            "Normal" -> {
                color = Color.parseColor("#A8A77A")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Fire" -> {
                color = Color.parseColor("#EE8130")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Water" -> {
                color = Color.parseColor("#6390F0")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Electric" -> {
                color = Color.parseColor("#F7D02C")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Grass" -> {
                color = Color.parseColor("#7AC74C")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Ice" -> {
                color = Color.parseColor("#96D9D6")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Fighting" -> {
                color = Color.parseColor("#C22E28")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Poison" -> {
                color = Color.parseColor("#A33EA1")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Ground" -> {
                color = Color.parseColor("#E2BF65")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Flying" -> {
                color = Color.parseColor("#A98FF3")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Psychic" -> {
                color = Color.parseColor("#F95587")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Bug" -> {
                color = Color.parseColor("#A6B91A")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Rock" -> {
                color = Color.parseColor("#B6A136")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Ghost" -> {
                color = Color.parseColor("#735797")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Dragon" -> {
                color = Color.parseColor("#6F35FC")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Dark" -> {
                color = Color.parseColor("#705746")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Steel" -> {
                color = Color.parseColor("#B7B7CE")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
            "Fairy" -> {
                color = Color.parseColor("#D685AD")
                frame.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
        Log.d("color", color.toString())
        if (types.contains("|")) {
            pokemonType1.text = types.substring(0, types.indexOf("|"))
            pokemonType2 = findViewById(R.id.type2)
            val constraintSet = ConstraintSet()
            constraintSet.clone(findViewById<ConstraintLayout>(R.id.pokemon_info_layout))
            constraintSet.connect(frame.id, ConstraintSet.END, R.id.guideline, ConstraintSet.START)
            constraintSet.setHorizontalBias(frame.id, 0.9f)
            constraintSet.applyTo(findViewById(R.id.pokemon_info_layout))
            frame = findViewById(R.id.frame2)
            frame.visibility = View.VISIBLE
            types = types.substring(types.indexOf("|") + 1, types.length)
            pokemonType2.text = types
            Log.d("secondtype", types)
            when (types) {
                "Normal" -> {
                    color = Color.parseColor("#A8A77A")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Fire" -> {
                    color = Color.parseColor("#EE8130")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Water" -> {
                    color = Color.parseColor("#6390F0")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Electric" -> {
                    color = Color.parseColor("#F7D02C")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Grass" -> {
                    color = Color.parseColor("#7AC74C")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Ice" -> {
                    color = Color.parseColor("#96D9D6")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Fighting" -> {
                    color = Color.parseColor("#C22E28")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Poison" -> {
                    color = Color.parseColor("#A33EA1")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Ground" -> {
                    color = Color.parseColor("#E2BF65")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Flying" -> {
                    color = Color.parseColor("#A98FF3")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Psychic" -> {
                    color = Color.parseColor("#F95587")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Bug" -> {
                    color = Color.parseColor("#A6B91A")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Rock" -> {
                    color = Color.parseColor("#B6A136")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Ghost" -> {
                    color = Color.parseColor("#735797")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Dragon" -> {
                    color = Color.parseColor("#6F35FC")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Dark" -> {
                    color = Color.parseColor("#705746")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Steel" -> {
                    color = Color.parseColor("#B7B7CE")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
                "Fairy" -> {
                    color = Color.parseColor("#D685AD")
                    frame.backgroundTintList = ColorStateList.valueOf(color)
                }
            }
            Log.d("color2", color.toString())
        } else {
            pokemonType1.text = types
        }

    }
}