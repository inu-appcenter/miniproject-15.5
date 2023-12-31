package com.example.study_choi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface UserDAO {
    @Insert
    fun insertUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)


}