package br.diogo.kotlinrepolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Repository(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "forks_count") var forks_count: Int,
    @ColumnInfo(name = "stargazers_count") var stargazers_count: Int,
    @Embedded var owner: Owner
)