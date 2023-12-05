package com.example.projectsample

import com.example.projectsample.model.BaseClass
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiCall {

    @GET("categories")
    fun getCategories() : Single<List<String>>

    @GET("products?limit=100") //extension
    fun getData() : Call<BaseClass>

    @GET
    fun getCategorizedProduct(@Url text : String) : Call<BaseClass>

    @GET("smartphones")
    fun getSmartPhonesFromAPI() : Single<BaseClass>
}