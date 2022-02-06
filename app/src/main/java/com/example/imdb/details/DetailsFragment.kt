package com.example.imdb.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.imdb.*
import com.example.imdb.databinding.FragmentDetailsBinding
import com.example.imdb.list.MoviesAdaptor
import com.example.imdb.models.Movie
import com.example.imdb.network.ApiResult

class DetailsFragment : Fragment() {
    private val viewModel by lazy {
        val factory = factory(MainViewModelInjector())
        ViewModelProvider(requireActivity(),factory)[MainViewModel::class.java]
    }
    lateinit var binding:FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.selectedMovie.observe(viewLifecycleOwner){
           if (it==null){
               return@observe
           }
            when(it){
                is ApiResult.Error -> {

                }
                is ApiResult.Loading -> {

                }
                is ApiResult.Success -> {
                    binding.data = it.data
                }
            }
        }
    }

}

@BindingAdapter("loadBackDrop")
fun loadBackDrop (view: ImageView?, data: Movie?){
    if (view == null || data == null ){
        return
    }
    val index = ImdbRepository.configuration.imagesConfiguration?.backdropSizes?.size!!-1
    Glide.with(view)
        .load(BASE_IMAGE + ImdbRepository.configuration.imagesConfiguration?.backdropSizes!![index] + data.backdropPath)
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(view)
}
