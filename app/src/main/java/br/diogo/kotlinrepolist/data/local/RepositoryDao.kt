package br.diogo.kotlinrepolist.data.local


import androidx.room.*
import br.diogo.kotlinrepolist.data.model.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository")
    fun getAll(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg repositories: Repository)

    @Delete
    fun delete(repository: Repository)
}