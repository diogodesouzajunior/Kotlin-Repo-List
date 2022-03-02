package br.diogo.kotlinrepolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.diogo.kotlinrepolist.data.RepoDataSource
import br.diogo.kotlinrepolist.repos.ReposViewModel

class ReposViewModelFactory constructor(
    private val repository: RepoDataSource,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ReposViewModel::class.java) ->
                    ReposViewModel(repository, application)
                else ->
                    throw IllegalArgumentException("Classe desconhecida.")
            }
        } as T
}