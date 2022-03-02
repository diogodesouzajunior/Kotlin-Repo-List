package br.diogo.kotlinrepolist.repos

import android.app.Application
import androidx.lifecycle.*
import br.diogo.kotlinrepolist.R
import br.diogo.kotlinrepolist.data.RepoDataSource
import br.diogo.kotlinrepolist.data.model.Repository

class ReposViewModel(val repository: RepoDataSource, application: Application) :
    AndroidViewModel(application), LifecycleObserver {

    val results = ArrayList<Repository>()
    val repos = MutableLiveData<List<Repository>>()
    val newItems = MutableLiveData<List<Repository>>()
    val offline = MutableLiveData<Boolean>().apply { value = false }

    val loadingVisibility = MutableLiveData<Boolean>().apply { value = false }
    val message = MutableLiveData<String>().apply { value = "" }

    var page = 1

    fun bumpPage() {
        page++
        offline.postValue(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun load() {
        if (page == 1)
            loadingVisibility.value = true

        message.value = ""
        repository.listAll({ items ->

            if (items.isEmpty()) {
                message.value = getApplication<Application>().getString(R.string.repo_empty)
            }
            loadingVisibility.value = false

            results.addAll(items)

            if (results.isEmpty())
                repos.postValue(items)
            else
                newItems.postValue(items)

        }, {
            message.value = getApplication<Application>().getString(R.string.repo_failed)
            loadingVisibility.value = false
        }, {
            offline.postValue(true)
        },
            page
        )
    }

}