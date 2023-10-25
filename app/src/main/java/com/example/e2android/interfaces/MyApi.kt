package com.example.e2android.interfaces

import com.example.e2android.Comments
import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("Comments")
    fun getComments():Call<List<Comments>>
}