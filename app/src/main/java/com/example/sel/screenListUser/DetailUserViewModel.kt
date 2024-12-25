package com.example.sel.screenListUser

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.sel.base.BasicViewModel
import com.example.sel.base.model.DataUserModel
import com.example.sel.screenListUser.repositoryUser.UserRepository


class DetailUserViewModel(application: Application) : BasicViewModel(application) {
    private val repository: UserRepository = UserRepository(application)
    val userData = MutableLiveData<List<DataUserModel>>()

    fun loadUsers() {
        val userList = repository.loadUsers()
        userData.postValue(userList)
    }
    fun deleteUser(userId: Int) {
        val updatedUsers = repository.deleteUserById(userId)
        userData.postValue(updatedUsers)
    }
    val title = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val description = MutableLiveData<String>()

}