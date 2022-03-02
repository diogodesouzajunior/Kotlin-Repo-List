package br.diogo.kotlinrepolist.data.remote

import br.diogo.kotlinrepolist.data.model.Repository

data class RepoCeoResponse(var status: String? = null, var items: List<Repository>? = null)