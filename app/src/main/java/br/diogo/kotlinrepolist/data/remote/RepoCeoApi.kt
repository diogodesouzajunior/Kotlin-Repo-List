package br.diogo.kotlinrepolist.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DogCeoApi {

    @GET("search/repositories?q=language:kotlin")
    fun listRepos(@Query("sort") sort: String, @Query("page") page: Int): Call<DogCeoResponse>

}