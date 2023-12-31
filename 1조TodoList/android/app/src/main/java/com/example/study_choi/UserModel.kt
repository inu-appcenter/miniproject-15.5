package com.example.study_choi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Long,
    @ColumnInfo val email: String,
    @ColumnInfo val password: String,
    @ColumnInfo val name: String,
)