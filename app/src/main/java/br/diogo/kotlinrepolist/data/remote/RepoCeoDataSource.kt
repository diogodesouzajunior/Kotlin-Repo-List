package br.diogo.kotlinrepolist.data.remote

import br.diogo.kotlinrepolist.data.RepoDataSource
import br.diogo.kotlinrepolist.data.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogCeoDataSource(val dogCeoApi: DogCeoApi) : RepoDataSource {
    override fun save(repository: Repository) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun listAll(
        success: (List<Repository>) -> Unit,
        failure: () -> Unit,
        offline: () -> Unit,
        page: Int
    ) {
        val call = dogCeoApi.listRepos("stars", page)
        call.enqueue(object : Callback<DogCeoResponse> {

            override fun onResponse(
                call: Call<DogCeoResponse>,
                response: Response<DogCeoResponse>
            ) {
                if (response.isSuccessful) {
                    val repos = mutableListOf<Repository>()
                    response.body()?.items?.forEach {
                        repos.add(it)
                    }
                    success(repos)
                } else {
                    failure()
                }
            }

            override fun onFailure(call: Call<DogCeoResponse>, t: Throwable?) {
                failure()
            }
        })

    }
}