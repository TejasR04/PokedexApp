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
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray


class PokemonInfoActivity : AppCompatActivity() {
    private lateinit var url : String
    private lateinit var name : String
    private lateinit var types : String
    private lateinit var abilityMap : MutableMap<String, String>
    private lateinit var dexEntry : String
    private lateinit var evolutionMap : MutableMap<String, String>
    private var evolutionUrl : String = ""
    private var height: Int = 0
    private var weight: Int = 0
    private lateinit var pokemonName : TextView
    private lateinit var pokedexNumber : TextView
    private lateinit var pokemonImage : ImageView
    private lateinit var pokemonType1 : TextView
    private lateinit var pokemonType2 : TextView
    private lateinit var shinyButton : Button
    private lateinit var frame : FrameLayout
    private lateinit var dexText : TextView
    private lateinit var abilityText : TextView
    private lateinit var speciesText : TextView
    private lateinit var heightText : TextView
    private lateinit var weightText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_info)
        url = intent.getStringExtra("url").toString()
        name = intent.getStringExtra("name").toString()
        types = intent.getStringExtra("type").toString()
        pokemonName = findViewById(R.id.pokemonName)
        pokemonName.text = name
        pokedexNumber = findViewById(R.id.pokedexNumber)
        val zeroes = "0".repeat(4 - (url.substring(34, url.length)).length)
        pokedexNumber.text = "No." + zeroes + (url.substring(34, url.length))
        pokemonImage = findViewById(R.id.pokemonPicture)
        dexText = findViewById(R.id.dexEntry)
        abilityText = findViewById(R.id.abilities)
        speciesText = findViewById(R.id.species)
        heightText = findViewById(R.id.height)
        weightText = findViewById(R.id.weight)
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
        } else {
            pokemonType1.text = types
        }
        getBasicInfo(url, object : PokemonInfoCallback {
            override fun onSuccess(evoDict : MutableMap<String, String>) {}
            override fun onSuccess(evoUrl : String, entry : String, species : String) {}
            override fun onSuccess(abilities: MutableMap<String, String>, h: Int, w: Int) {
                abilityMap = abilities
                height = h
                weight = w
                Log.d("abilities", abilityMap.toString())
                val abilityString = StringBuilder()
                var count = 0
                for (ability in abilityMap.keys) {
                    abilityString.append(ability.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                    count++
                    if (count != abilityMap.size) {
                        abilityString.append(" | ")
                    }
                }
                abilityText.text = abilityString
                heightText.text = (height/10.0).toString() + " m"
                weightText.text = (weight/10.0).toString() + " kg"
            }
            override fun onSuccess(urls: MutableList<String>, names: MutableList<String>, types: MutableList<String>) {}
            override fun onFailure(error: String) {
            }
        })
        getSpecificInfo(url, object : PokemonInfoCallback {
            override fun onSuccess(evoDict : MutableMap<String, String>) {}
            override fun onSuccess(evoUrl : String, entry : String, species : String) {
                evolutionUrl = evoUrl
                dexEntry = entry
                getEvolutionInfo(evolutionUrl, object : PokemonInfoCallback {
                    override fun onSuccess(evoDict : MutableMap<String, String>) {
                        evolutionMap = evoDict
                    }
                    override fun onSuccess(evoUrl : String, entry : String, species : String) {}
                    override fun onSuccess(abilities: MutableMap<String, String>, h: Int, w: Int) {}
                    override fun onSuccess(urls: MutableList<String>, names: MutableList<String>, types: MutableList<String>) {}
                    override fun onFailure(error: String) {
                    }
                })
                dexText.text = dexEntry
                speciesText.text = species
            }
            override fun onSuccess(abilities: MutableMap<String, String>, h: Int, w: Int) {}
            override fun onSuccess(urls: MutableList<String>, names: MutableList<String>, types: MutableList<String>) {}
            override fun onFailure(error: String) {
            }
        })
    }

    private fun getBasicInfo(urlInput: String, callback: PokemonInfoCallback) {
        val client = AsyncHttpClient()
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val abilityJson = json.jsonObject.getJSONArray("abilities")
                val abilities = mutableMapOf<String, String>()
                for (i in 0 until abilityJson.length()) {
                    abilities[abilityJson.getJSONObject(i).getJSONObject("ability").getString("name")] =
                        abilityJson.getJSONObject(i).getJSONObject("ability").getString("url")
                }
                val height = json.jsonObject.getInt("height")
                val weight = json.jsonObject.getInt("weight")
                callback.onSuccess(abilities, height, weight)
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

    private fun getSpecificInfo(url: String, callback: PokemonInfoCallback) {
        val urlInput = url.substring(0, url.indexOf("pokemon") + 7) + "-species" + url.substring(url.indexOf("pokemon") + 7, url.length)
        val client = AsyncHttpClient()
        client[urlInput, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val evolutionUrl = json.jsonObject.getJSONObject("evolution_chain").getString("url")
                val entryJson = json.jsonObject.getJSONArray("flavor_text_entries")
                var entry : String = ""
                for (i in 0 until entryJson.length()) {
                    if (i > entryJson.length()) {
                        break
                    }
                    if (entryJson.getJSONObject(i).getJSONObject("language").getString("name") == "en") {
                        entry = entryJson.getJSONObject(i).getString("flavor_text").replace("\n", " ")
                        break
                    }
                }
                val species = json.jsonObject.getJSONArray("genera")
                var speciesEntry : String = ""
                for (i in 0 until species.length()) {
                    if (i > species.length()) {
                        break
                    }
                    if (species.getJSONObject(i).getJSONObject("language").getString("name") == "en") {
                        speciesEntry = species.getJSONObject(i).getString("genus")
                        break
                    }
                }
                callback.onSuccess(evolutionUrl, entry, speciesEntry)
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

    private fun getEvolutionInfo(url : String, callback : PokemonInfoCallback) {
        val client = AsyncHttpClient()
        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val chain = json.jsonObject.getJSONObject("chain")
                val evoDict = mutableMapOf<String, String>()
                val initialSpecies = chain.getJSONObject("species")
                evoDict[initialSpecies.getString("name")] = initialSpecies.getString("url")
                val evoJson = chain.getJSONArray("evolves_to")
                if (evoJson.length() > 0) {
                    getEvolutions(evoJson, evoDict)
                }
                callback.onSuccess(evoDict)
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
    fun getEvolutions(evoJson: JSONArray, evoDict: MutableMap<String, String>) {
        for (i in 0 until evoJson.length()) {
            val speciesJson = evoJson.getJSONObject(i).getJSONObject("species")
            evoDict[speciesJson.getString("name")] = speciesJson.getString("url")

            if (evoJson.getJSONObject(i).has("evolves_to")) {
                getEvolutions(evoJson.getJSONObject(i).getJSONArray("evolves_to"), evoDict)
            }
        }
    }
}