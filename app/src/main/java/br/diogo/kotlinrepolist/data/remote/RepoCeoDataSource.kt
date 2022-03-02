package br.diogo.kotlinrepolist.data.remote

import br.diogo.kotlinrepolist.data.RepoDataSource
import br.diogo.kotlinrepolist.data.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoCeoDataSource(val RepoCeoApi: RepoCeoApi) : RepoDataSource {
    override fun save(repository: Repository) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun listAll(
        success: (List<Repository>) -> Unit,
        failure: () -> Unit,
        offline: () -> Unit,
        page: Int
    ) {
        val call = RepoCeoApi.listRepos("stars", page)
        call.enqueue(object : Callback<RepoCeoResponse> {

            override fun onResponse(
                call: Call<RepoCeoResponse>,
                response: Response<RepoCeoResponse>
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

            override fun onFailure(call: Call<RepoCeoResponse>, t: Throwable?) {
                failure()
            }
        })

    }
}