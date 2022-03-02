package br.diogo.kotlinrepolist.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RepoCeoApi {

    @GET("search/repositories?q=language:kotlin")
    fun listRepos(@Query("sort") sort: String, @Query("page") page: Int): Call<RepoCeoResponse>

}