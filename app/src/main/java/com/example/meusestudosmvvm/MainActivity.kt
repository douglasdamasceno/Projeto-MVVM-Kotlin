package com.example.meusestudosmvvm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.meusestudosmvvm.adapter.MainAdapter
import com.example.meusestudosmvvm.databinding.ActivityMainBinding
import com.example.meusestudosmvvm.repository.MainRepository
import com.example.meusestudosmvvm.rest.RetrofitService
import com.example.meusestudosmvvm.viewmodel.main.MainViewModel
import com.example.meusestudosmvvm.viewmodel.main.MainViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter{
        openLink(it.link)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )
        binding.recycleView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("TESTE","live list observe")
            adapter.setLiveList(lives)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this,"erro = " + message, Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        Log.i("TESTE","onResume mainActivity")
        super.onResume()
        viewModel.getAllLives()
    }

    private fun openLink(link:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }


}