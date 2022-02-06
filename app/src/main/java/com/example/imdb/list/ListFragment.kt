package com.example.imdb.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imdb.MainViewModel
import com.example.imdb.MainViewModelInjector
import com.example.imdb.databinding.FragmentListBinding
import com.example.imdb.factory
import com.example.imdb.models.MovieResponse
import com.example.imdb.network.ApiResult

class ListFragment: Fragment() {
    private val viewModel by lazy {
        val factory = factory(MainViewModelInjector())
        ViewModelProvider(requireActivity(),factory)[MainViewModel::class.java]
    }
    private val adaptor by lazy {
        MoviesAdaptor(requireContext()){

        }
    }

    lateinit var binding : FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adaptor
        binding.etQuery.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_DONE){
                    val text = binding.etQuery.text.toString()
                    viewModel.query.value = if (text.isNotEmpty()) text else null
                    return true
                }
                return false
            }

        })
        viewModel.response.observe(viewLifecycleOwner,{
            if (it==null){
                return@observe
            }
            when(it){
                is ApiResult.Error -> {

                }
                is ApiResult.Loading -> {

                }
                is ApiResult.Success<MovieResponse> -> {
                    adaptor.list.clear()
                    adaptor.list.addAll(it.data.results!!)
                    adaptor.notifyDataSetChanged()
                }

            }
        })
    }




}
