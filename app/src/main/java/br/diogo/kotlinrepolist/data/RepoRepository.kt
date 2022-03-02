package br.diogo.kotlinrepolist.data

import br.diogo.kotlinrepolist.data.local.RepoCeoLocalDataSource
import br.diogo.kotlinrepolist.data.model.Repository

class RepoRepository(
    private val remoteDataSource: RepoDataSource,
    private val dogCeoLocalDataSource: RepoCeoLocalDataSource
) : RepoDataSource {
    override fun save(repository: Repository) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listAll(
        success: (List<Repository>) -> Unit,
        failure: () -> Unit,
        offline: () -> Unit,
        page: Int
    ) {

        remoteDataSource.listAll({
            it.forEach(dogCeoLocalDataSource::save)
            success(it)

        }, {
            dogCeoLocalDataSource.listAll(success, failure, offline, page)
        }, {}, page)
    }


}