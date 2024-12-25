package com.example.sel.screenListUser

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.sel.R
import com.example.sel.base.BasicViewModel
import com.example.sel.base.Constants
import com.example.sel.base.model.DataUserModel
import com.example.sel.base.model.ItemUserConverted
import com.example.sel.interfaces.ItemBaseModel
import com.example.sel.screenListUser.repositoryUser.UserRepository


class UserViewModel(application: Application) : BasicViewModel(application) {

    private val repository: UserRepository = UserRepository(application)
    val userData = MutableLiveData<List<DataUserModel>>()

    fun loadUsers() {
        val userList = repository.loadUsers()
        userData.postValue(userList)
    }

    fun resetUsersToDefault() {
        repository.copyJsonToInternalIfNeeded(forceReset = true)
    }

    val convertedItemUser = MutableLiveData<List<ItemBaseModel>>().also {
        it.value = null
    }
    var onClickItemUser: (ItemUserConverted) -> Unit = {}
    private fun onItemClickUser(item: ItemUserConverted) {
        onClickItemUser(item)
    }

    fun convertListUser(originalList: List<DataUserModel>?) {
        val userListToConvert = mutableListOf<ItemBaseModel>()
        originalList?.forEachIndexed { index, it ->
            val newHomeMenuItem = ItemUserConverted(
                id = it.index.toString(),
                title = it.title,
                date = it.date,
                description = it.description,
                onItemClick = :: onItemClickUser,
                layoutId = R.layout.item_user,
                viewType = 0
            )
            userListToConvert.add(newHomeMenuItem)
        }

        convertedItemUser.postValue(userListToConvert)
    }
    fun sortUsers(criteria: String) {
        val currentUsers = userData.value ?: emptyList()
        val sortedUsers = when (criteria) {
            Constants.BundleParam.INDEX  -> currentUsers.sortedBy { it.index }
            Constants.BundleParam.TITLE -> currentUsers.sortedBy { it.title }
            Constants.BundleParam.DATE  -> currentUsers.sortedBy { it.date }
            else -> currentUsers
        }
        userData.postValue(sortedUsers.reversed()) // Luôn sắp xếp giảm dần
    }

}