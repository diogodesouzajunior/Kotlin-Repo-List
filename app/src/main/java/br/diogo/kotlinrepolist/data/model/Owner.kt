package br.diogo.kotlinrepolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Owner(
    @PrimaryKey var node_id: String,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "avatar_url") var avatar_url: String?
)