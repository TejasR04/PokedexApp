package com.example.pokedexapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter (private var pokeList: List<String>, private var nameList: List<String>, private var weightList: List<String>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView
        val pokemonText: TextView
        val pokemonWeight: TextView
        init {
            pokemonImage = view.findViewById(R.id.pokemon_image)
            pokemonText = view.findViewById(R.id.pokemon_name)
            pokemonWeight = view.findViewById(R.id.pokemon_weight)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("check","test")
        Glide.with(holder.itemView)
            .load(pokeList[position])
            .fitCenter()
            .into(holder.pokemonImage)
        holder.pokemonText.text = nameList[position]
        holder.pokemonWeight.text = weightList[position]
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
    fun updateData(newPokemonImageList: MutableList<String>, newNameList: MutableList<String>, newWeightList: MutableList<String>) {
        pokeList = newPokemonImageList
        nameList = newNameList
        weightList = newWeightList
        notifyDataSetChanged()
    }
}
