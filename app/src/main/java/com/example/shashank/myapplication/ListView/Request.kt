package com.example.shashank.myapplication.ListView

import io.reactivex.Observable
import retrofit2.http.GET

interface RequestInterface {
    @GET("/tutorial/jsonparsetutorial.txt")
    fun getData() : Observable<Model>
}