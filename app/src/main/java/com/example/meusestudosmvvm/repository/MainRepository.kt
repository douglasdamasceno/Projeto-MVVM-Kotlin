package com.example.meusestudosmvvm.repository

import com.example.meusestudosmvvm.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService){

    fun getAllLives() = retrofitService.getAllLives()
}