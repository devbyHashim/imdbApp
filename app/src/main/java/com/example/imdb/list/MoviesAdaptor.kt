package com.example.imdb.list

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imdb.BASE_IMAGE
import com.example.imdb.ImdbRepository
import com.example.imdb.R
import com.example.imdb.databinding.RowMovieItemBinding
import com.example.imdb.models.Movie
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class MoviesAdaptor(private val context: Context , val onSelection : (movie:Movie) -> Unit) : RecyclerView.Adapter<MoviesAdaptor.Holder>() {
    val list by lazy {
        mutableListOf<Movie>()
    }

    inner class Holder(val binding:RowMovieItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RowMovieItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       val item = list[position]
        holder.binding.data = item
        holder.binding.root.setOnClickListener {
            onSelection(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

@BindingAdapter("loadPoster")
fun loadPoster (view:ImageView?,data:Movie?){
    if (view == null || data == null ){
        return
    }
    val index = ImdbRepository.configuration.imagesConfiguration?.posterSizes?.size!!-1
    Glide.with(view)
        .load(BASE_IMAGE + ImdbRepository.configuration.imagesConfiguration?.posterSizes!![index] + data.posterPath)
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(view)
}



@BindingAdapter("customTextColor")
fun customTextColor (view:TextView?,data:Movie?){
    if (view == null || data == null || data.releaseDate.isNullOrEmpty() ){
        return
    }
    val calender = Calendar.getInstance()
    val customCalender = Calendar.getInstance()
    customCalender.time = SimpleDateFormat("yyyy-MM-DD").parse(data.releaseDate)
    if (calender[Calendar.YEAR] == customCalender[Calendar.YEAR]){
        view.setTextColor(view.context.resources.getColor(android.R.color.holo_red_dark))
        view.setTypeface(null, Typeface.BOLD)
    }
    else{
        view.setTextColor(view.context.resources.getColor(android.R.color.white))
        view.setTypeface(null, Typeface.NORMAL)
    }
}