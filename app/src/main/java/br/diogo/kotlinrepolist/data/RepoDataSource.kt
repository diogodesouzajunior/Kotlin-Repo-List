package br.diogo.kotlinrepolist.data

import br.diogo.kotlinrepolist.data.model.Repository

interface RepoDataSource {

    fun listAll(success: (List<Repository>) -> Unit, failure: () -> Unit, offline: () -> Unit, page: Int)

    fun save(repository: Repository)

}