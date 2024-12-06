package com.example.eventapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {
    @GET("/allEvents")
   fun getEvents(): Call<List<Event>>

    @GET("/events/location/{location}")
    fun getEventsByLocation(@Path("location") location: String): Call<List<Event>>

    @GET("/events/type/{eventType}")
    fun getEventsByCategory(@Path("eventType") eventType: String): Call<List<Event>>

    @GET("/findEvent/{id}")
    fun getEventById(@Path("id") eventId: String): Call<Event>
}