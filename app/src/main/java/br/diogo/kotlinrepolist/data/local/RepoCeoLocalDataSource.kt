package br.diogo.kotlinrepolist.data.local

import br.diogo.kotlinrepolist.data.RepoDataSource
import br.diogo.kotlinrepolist.data.model.Repository
import br.diogo.kotlinrepolist.util.AppExecutors

class RepoCeoLocalDataSource(
    private val dao: RepositoryDao,
    private val appExecutors: AppExecutors
) : RepoDataSource {

    override fun save(repository: Repository) {
        appExecutors.roomThreadExecutor.execute {
            try {
                dao.insertAll(repository)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun listAll(
        success: (List<Repository>) -> Unit,
        failure: () -> Unit,
        offline: () -> Unit,
        page: Int
    ) {
        appExecutors.roomThreadExecutor.execute {
            val repos = dao.getAll()
            appExecutors.mainThreadExecutor.execute { success(repos) }
        }

        offline()
    }

}