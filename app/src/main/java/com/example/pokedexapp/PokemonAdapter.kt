package com.example.pokedexapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PokemonAdapter (private var urlList : List<String>, private var nameList: List<String>, private var typeList: List<String>): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView
        val pokemonText: TextView
        val pokedexNumber: TextView
        val pokemonType: TextView
        init {
            pokemonImage = view.findViewById(R.id.pokemon_image)
            pokemonText = view.findViewById(R.id.pokemon_name)
            pokedexNumber = view.findViewById(R.id.pokedex_number)
            pokemonType = view.findViewById(R.id.pokemon_type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position+1}.png")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.pokemonImage)
        holder.pokemonText.text = nameList[position].replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        val zeroes = "0".repeat(4 - (position+1).toString().length)
        holder.pokedexNumber.text = "No." + zeroes +  (position+1).toString()
        holder.pokemonType.text = typeList[position]
        Log.d("PokemonAdapter", nameList.size.toString())
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PokemonInfoActivity::class.java)
            intent.putExtra("url", urlList[position])
            intent.putExtra("name", nameList[position])
            intent.putExtra("type", typeList[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
    fun updateData(newURLList: MutableList<String>, newNameList: MutableList<String>, newTypeList: MutableList<String>) {
        urlList = newURLList
        nameList = newNameList
        typeList = newTypeList
        notifyDataSetChanged()
    }
}
