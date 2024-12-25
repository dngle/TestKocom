package com.example.sel.screenListUser.repositoryUser

import android.content.Context
import com.example.sel.base.model.DataUserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class UserRepository(private val context: Context) {
    private val fileName = "listUser.json"
    private val file: File
        get() = File(context.filesDir, fileName)

     fun copyJsonToInternalIfNeeded(forceReset: Boolean = false) {
        if (forceReset || !file.exists()) {
            context.assets.open(fileName).use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }

    fun loadUsers(): List<DataUserModel> {
        copyJsonToInternalIfNeeded()
        return try {
            val jsonString = file.readText()
            val type = object : TypeToken<List<DataUserModel>>() {}.type
            Gson().fromJson(jsonString, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveUsers(users: List<DataUserModel>) {
        val jsonString = Gson().toJson(users)
        file.writeText(jsonString)
    }

    fun deleteUserById(userId: Int): List<DataUserModel> {
        val currentUsers = loadUsers()
        val updatedUsers = currentUsers.filter { it.index != userId }
        saveUsers(updatedUsers)
        return updatedUsers
    }
}

