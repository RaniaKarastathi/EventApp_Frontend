package com.example.eventapp


import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("events")
   fun getEvents(): Call<List<Event>>
}