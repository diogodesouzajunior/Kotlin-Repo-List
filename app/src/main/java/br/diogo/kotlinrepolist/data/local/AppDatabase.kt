package br.diogo.kotlinrepolist.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import br.diogo.kotlinrepolist.data.model.Repository

@Database(entities = arrayOf(Repository::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}