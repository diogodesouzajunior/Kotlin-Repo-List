package br.diogo.kotlinrepolist

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.diogo.kotlinrepolist.data.RepoRepository
import br.diogo.kotlinrepolist.data.local.AppDatabase
import br.diogo.kotlinrepolist.data.local.RepoCeoLocalDataSource
import br.diogo.kotlinrepolist.data.local.RepositoryDao
import br.diogo.kotlinrepolist.data.model.Repository
import br.diogo.kotlinrepolist.data.model.interfaces.IAdapterDataSource
import br.diogo.kotlinrepolist.data.remote.DogCeoApi
import br.diogo.kotlinrepolist.data.remote.DogCeoDataSource
import br.diogo.kotlinrepolist.databinding.ReposFragmentBinding
import br.diogo.kotlinrepolist.repos.ReposAdapter
import br.diogo.kotlinrepolist.repos.ReposViewModel
import br.diogo.kotlinrepolist.util.AppExecutors
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReposFragment : Fragment() {

    lateinit var viewModel: ReposViewModel

    companion object {
        fun newInstance() = ReposFragment()
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ReposFragmentBinding =
            ReposFragmentBinding.inflate(inflater, container, false)

        this.activity?.application?.let {
            viewModel = createViewModel(it)
            binding.viewModel = viewModel
            binding.recyclerView.adapter = ReposAdapter(this.context, ArrayList<Repository>())
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )

            viewModel.repos.observe(
                viewLifecycleOwner,
                Observer {
                    binding.recyclerView.adapter =
                        ReposAdapter(this.context, it.toMutableList())
                }
            )

            viewModel.newItems.observe(
                viewLifecycleOwner,
                Observer {
                    (binding.recyclerView.adapter as? ReposAdapter)?.addElements(it)
                }
            )

            viewModel.offline.observe(
                viewLifecycleOwner,
                Observer {
                    (binding.recyclerView.adapter as? ReposAdapter)?.offline = it
                }
            )


            (binding.recyclerView.adapter as? ReposAdapter)?.setAdapterInteractionListener(object :
                IAdapterDataSource {
                override fun adapterUserWantsLoadMoreData(apadter: RecyclerView.Adapter<*>?) {
                    viewModel.bumpPage()
                    viewModel.load()
                }
            })

            binding.setLifecycleOwner(this)
            this.lifecycle.addObserver(viewModel)
        }

        return binding.root
    }

    fun createViewModel(application: Application): ReposViewModel {
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val dogCeoDataSource = DogCeoDataSource(retrofit.create(DogCeoApi::class.java))
        val localDataSource = RepoCeoLocalDataSource(repoDao(application), AppExecutors())
        val repository = RepoRepository(dogCeoDataSource, localDataSource)

        val factory = ReposViewModelFactory(repository, application)

        return ViewModelProviders.of(this, factory).get(ReposViewModel::class.java)
    }


    fun repoDao(applicationContext: Application): RepositoryDao {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "repos-app"
        ).build().repositoryDao()
    }


}
